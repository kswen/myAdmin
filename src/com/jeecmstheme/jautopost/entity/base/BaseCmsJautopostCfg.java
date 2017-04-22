package com.jeecmstheme.jautopost.entity.base;

import java.io.Serializable;

import com.jeecmstheme.jautopost.entity.CmsJautopost;
import com.jeecmstheme.jautopost.entity.CmsJautopostFiles;


/**
 * This is an object that contains data related to the jc_jautopost_files table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jc_jautopost_config"
 */

public abstract class BaseCmsJautopostCfg  implements Serializable {
	
	private static final long serialVersionUID = -7877416532545790396L;
	public static String REF = "CmsJautopostCfg";
	public static String PROP_ID = "id";
	public static String PROP_NAME = "name";
	public static String PROP_DOMAIN = "domain";
	public static String PROP_BUCKET = "bucket";
	public static String PROP_ACCESS_KEY = "accessKey";
	public static String PROP_SECRET_KEY = "secretKey";
	public static String PROP_LOCAL_SAVE = "localSave";

	// constructors
	public BaseCmsJautopostCfg () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCmsJautopostCfg (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseCmsJautopostCfg (
		java.lang.Integer id,
		java.lang.String name,
		java.lang.String domain,
		java.lang.String bucket,
		java.lang.String accessKey,
		java.lang.String secretKey,
		java.lang.String localSave) {

		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String name;
	private java.lang.String domain;
	private java.lang.String bucket;
	private java.lang.String accessKey;
	private java.lang.String secretKey;
	private java.lang.String localSave;

	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="jc_jautopost_config_id"
     */
	public java.lang.Integer getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.Integer id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}

	/**
	 * @return the name
	 */
	public java.lang.String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}

	/**
	 * @return the domain
	 */
	public java.lang.String getDomain() {
		return domain;
	}

	/**
	 * @param domain the domain to set
	 */
	public void setDomain(java.lang.String domain) {
		this.domain = domain;
	}

	/**
	 * @return the bucket
	 */
	public java.lang.String getBucket() {
		return bucket;
	}

	/**
	 * @param bucket the bucket to set
	 */
	public void setBucket(java.lang.String bucket) {
		this.bucket = bucket;
	}

	/**
	 * @return the accessKey
	 */
	public java.lang.String getAccessKey() {
		return accessKey;
	}

	/**
	 * @param accessKey the accessKey to set
	 */
	public void setAccessKey(java.lang.String accessKey) {
		this.accessKey = accessKey;
	}

	/**
	 * @return the secretKey
	 */
	public java.lang.String getSecretKey() {
		return secretKey;
	}

	/**
	 * @param secretKey the secretKey to set
	 */
	public void setSecretKey(java.lang.String secretKey) {
		this.secretKey = secretKey;
	}

	/**
	 * @return the localSave
	 */
	public java.lang.String getLocalSave() {
		return localSave;
	}

	/**
	 * @param localSave the localSave to set
	 */
	public void setLocalSave(java.lang.String localSave) {
		this.localSave = localSave;
	}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof CmsJautopost)) return false;
		else {
			CmsJautopostFiles cmsJautopostFile = (CmsJautopostFiles) obj;
			if (null == this.getId() || null == cmsJautopostFile.getId()) return false;
			else return (this.getId().equals(cmsJautopostFile.getId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}

	public String toString () {
		return super.toString();
	}
}