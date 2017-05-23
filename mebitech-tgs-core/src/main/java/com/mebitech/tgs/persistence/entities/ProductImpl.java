package com.mebitech.tgs.persistence.entities;

import com.mebitech.core.api.persistence.entities.security.IEntitySecurity;
import com.mebitech.core.api.persistence.enums.CrudType;
import com.mebitech.tgs.core.api.persistence.entities.IProduct;
import org.codehaus.jackson.map.ObjectMapper;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

/**
 * Entity class for product.
 * 
 * @see com.mebitech.kep.core.api.persistence.entities.IProduct
 *
 */
@Entity
@Table(name = "C_PRODUCT")
public class ProductImpl implements IProduct {

	private static final long serialVersionUID = 3120888411065795936L;

	@Id
	@GeneratedValue
	@Column(name = "ID", unique = true, nullable = false)
	private Long id;

	@Column(name = "PRODUCTID", nullable = false, unique = true)
	private int productId; 

	@Column(name = "PRODUCTADI", nullable = false, unique = true)
	private String productAdi; 
	
	
	@Column(name = "IS_DELETED")
	private Boolean deleted;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE", nullable = false)
	private Date createDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFY_DATE")
	private Date modifyDate;
	
	private CrudType[] crudTypes;
	
	private String[] entityAttributes;

	public ProductImpl() {
	}

	public ProductImpl(Long id, int productId,String productAdi, Boolean deleted, Date createDate, Date modifyDate
			) {
		super();
		this.id = id;
		this.productId = productId;
		this.productAdi = productAdi;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		
	}

	public ProductImpl(IProduct il) {
		this.id = il.getId();
		this.deleted = il.getDeleted();
		this.productId = il.getProductId();
		this.productAdi = il.getProductAdi();
		this.createDate = il.getCreateDate();
		this.modifyDate = il.getModifyDate();

		
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	@Override
	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}



	@Override
	public String toJson() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String toString() {
		return "ProductImpl [id=" + id + ", productId=" + productId + ", deleted=" + deleted + 
				", createDate=" + createDate + ", modifyDate=" + modifyDate + "]";
	}

	@Override
	public Map<String, String> getProperties() {
		//TODO: Return Table Column Name;
		return null;
	}
	
	@Override
	public String getProductAdi() {
		return productAdi;
	}

	public void setProductAdi(String productAdi) {
		this.productAdi = productAdi;
	}

	@Override
	public void applyEntitySecurity(IEntitySecurity entitySecurity) {
		crudTypes = entitySecurity.getCrudType();
		entityAttributes = entitySecurity.getEntityAttributes();
	}

	
}
