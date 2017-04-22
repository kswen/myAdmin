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
 *  table="jc_jautopost_files"
 */

public abstract class BaseCmsJautopostFiles  implements Serializable {
	
	private static final long serialVersionUID = -1882368357585689940L;
	public static String REF = "CmsJautopostFiles";
	public static String PROP_ID = "id";
	public static String PROP_MD5_STR = "md5Str";
	public static String PROP_PATH = "path";


	// constructors
	public BaseCmsJautopostFiles () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCmsJautopostFiles (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseCmsJautopostFiles (
		java.lang.Integer id,
		java.lang.String md5Str,
		java.lang.String path) {

		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String md5Str;
	private java.lang.String path;

	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="jautopost_files_id"
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
	 * @return the md5Str
	 */
	public java.lang.String getMd5Str() {
		return md5Str;
	}

	/**
	 * @param md5Str the md5Str to set
	 */
	public void setMd5Str(java.lang.String md5Str) {
		this.md5Str = md5Str;
	}

	/**
	 * @return the path
	 */
	public java.lang.String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(java.lang.String path) {
		this.path = path;
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