package com.mebitech.web.security;

import com.mebitech.core.api.configuration.IConfigurationService;
import com.mebitech.core.api.persistence.entities.IUserLevel;
import com.mebitech.core.api.rest.enums.RestResponseStatus;
import com.mebitech.core.api.rest.processors.IUserLevelRequestProcessor;
import com.mebitech.core.api.rest.processors.IUserRequestProcessor;
import com.mebitech.core.api.rest.responses.IRestResponse;
import com.mebitech.persistence.filter.FilterAndPagerImpl;
import com.mebitech.persistence.filter.FilterImpl;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.ldap.AbstractLdapRealm;
import org.apache.shiro.realm.ldap.LdapContextFactory;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Main realm class which works as a security-specific DAO for Shiro to
 * understand Subject programming API.
 * @see http://shiro.apache.org/terminology.html
 *
 */
public class MebitechLdapRealm extends AbstractLdapRealm {

	private static Logger logger = LoggerFactory.getLogger(MebitechLdapRealm.class);

	private boolean useSSL;
	private String objectClasses;
	private String userIdAttribute;

	@Autowired
	private IConfigurationService config;

	@Autowired
	private IUserRequestProcessor userRequestProcessor;

	@Autowired
	private IUserLevelRequestProcessor userLevelRequestProcessor;


	private DefaultWebSecurityManager securityManager;

	public void initRealm() {

		logger.debug("Initializing LDAP realm.");

		logger.info("Successfully initialized LDAP realm.");
	}

	public void reload(Map<String, ?> properties) {
		logger.debug("Config admin update received: {} ", properties);
		this.searchBase = (String) properties.get("searchBase");
	}

	@Override
	public void setSystemPassword(String systemPassword) {
		super.setSystemPassword(systemPassword);
	}

	@Override
	protected AuthenticationInfo queryForAuthenticationInfo(AuthenticationToken token,
		LdapContextFactory contextFactory) throws NamingException {

		logger.debug("queryForAuthenticationInfo, principal: {}, credentials: *****", token.getPrincipal());
		logger.debug("contextFactory : {}", contextFactory);

		try {
			if (token == null || token.getPrincipal() == null) {
				logger.info("No authentication token provided, will not try to authenticate..");
				return null;
			}
			String userName = ((UsernamePasswordToken)token).getUsername();
			String password = new String(((UsernamePasswordToken)token).getPassword());

			Long levelId = 0L;
			if(userName.indexOf("#") > -1) {//userName admin#1 şeklinde level ile birlikte gelirse
				String[] userLevel = userName.split("#");

				userName = userLevel[0];

				levelId = Long.valueOf(userLevel[1]);
			} else if(!userName.equals("") && !password.equals("")){
				FilterAndPagerImpl filterAndPager = new FilterAndPagerImpl();
				filterAndPager.setFilters(new ArrayList<FilterImpl>());
				FilterImpl uFilter = new FilterImpl();
				uFilter.setProperty("user.userName");
				uFilter.setValue(userName);
				uFilter.setOperator("=");
				filterAndPager.getFilters().add(uFilter);
				FilterImpl pFilter = new FilterImpl();
				pFilter.setProperty("user.password");
				pFilter.setValue(password);
				pFilter.setOperator("=");
				filterAndPager.getFilters().add(pFilter);

				IRestResponse response = userLevelRequestProcessor.getLevels(filterAndPager);
				if(response != null && response.getResultMap() != null && response.getResultMap().get("data") != null){
					levelId = ((IUserLevel)((List)response.getResultMap().get("data")).get(0)).getLevel().getId();//(Long) ((Map)((Map)((List)response.getResultMap().get("data")).get(0)).get("level")).get("id");
				}
			}

			IRestResponse response = userRequestProcessor.findByUserNameAndPassword(userName,password,levelId);

			if(response.getStatus() == RestResponseStatus.OK){
				Long userLevelId = (Long) response.getResultMap().get("data");
				((UsernamePasswordToken) token).setUsername(((UsernamePasswordToken) token).getUsername()+"#"+userLevelId.toString());
				System.out.println(token.getPrincipal());
				//return new SimpleAuthenticationInfo(token,token.getCredentials(),"StaticRealm");

				return new SimpleAccount(token,token.getCredentials(),"StaticRealm");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return null;
	}

	@Override
	protected AuthorizationInfo queryForAuthorizationInfo(PrincipalCollection principalCollection,
		LdapContextFactory contextFactory) throws NamingException {
		logger.debug("queryForAuthorizationInfo, principalCollection.getPrimaryPrincipal: {}",
			principalCollection.getPrimaryPrincipal());
		logger.debug("contextFactory : {}", contextFactory);
		return null;
	}

	private String mergeFiltersOR(String filter1, String filter2) {
		String ORTemplate = "(|({0})({1}))";
		return ORTemplate.replace("{0}", filter1).replace("{1}", filter2);
	}

	private String mergeFiltersAND(String filter1, String filter2) {
		String ORTemplate = "(&({0})({1}))";
		return ORTemplate.replace("{0}", filter1).replace("{1}", filter2);
	}

	private String createObjectClassFilter(String objectClassesCommaSeparatedValues) {

		String[] objectClasses = objectClassesCommaSeparatedValues.split(",");
		String[] filters = new String[objectClasses.length];

		String filterTemplate = "objectClass={0}";

		for (int i = 0; i < objectClasses.length; i++) {
			String objectClassFilter = filterTemplate.replace("{0}", objectClasses[i]);
			filters[i] = objectClassFilter;
		}

		String resultingFilter = null;
		for (int i = 0; i < filters.length; i++) {
			if (null != resultingFilter) {
				resultingFilter = mergeFiltersOR(resultingFilter, filters[i]);
			} else {
				resultingFilter = filters[i];
			}
		}

		return resultingFilter;
	}

	private String createAttributeFilter(String attributeName, String attributeValue) {
		String filterTemplate = "{0}={1}";

		return filterTemplate.replace("{0}", attributeName).replace("{1}", attributeValue);
	}

	private SearchControls getSimpleSearchControls() {
		SearchControls searchControls = new SearchControls();
		searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		searchControls.setTimeLimit(5000);
		return searchControls;
	}

	public void setSecurityManager(DefaultWebSecurityManager securityManager) {
		this.securityManager = securityManager;
	}
}
