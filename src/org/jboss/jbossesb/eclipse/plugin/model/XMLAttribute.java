package org.jboss.jbossesb.eclipse.plugin.model;

import java.util.List;

/**
 * <h3>Class represents a XML Attribute</h3>
 * <ul>
 * <li><i>type</i> - data type of attribute (String, int, boolean)</li>
 * <li><i>required</i> - flag if is the attribute required (true - must be set)</li>
 * <li><i>allowedValues</i> - list of allowed values for the attribute</li>
 * <li><i>value</i> - value of the attribute</li>
 * <li><i>name</i> - text representation of the element for displaying in GUI</li>
 * <li><i>hint</i> - complex description of the attribute for displaying in GUI</li>
 * </ul> 
 * 
 * @author Tomas Sedmik, tomas.sedmik@gmail.com
 * @since 2012-09-10
 */

public class XMLAttribute {
	
	private String type;
	private boolean required;
	private List<String> allowedValues;
	private String value;
	private String name;
	private String hint;
	private String xmlName;
	
	public XMLAttribute(String type, boolean required,
			List<String> allowedValues, String value, String name, String hint, String xmlName) {
		super();
		this.type = type;
		this.required = required;
		this.allowedValues = allowedValues;
		this.value = value;
		this.name = name;
		this.hint = hint;
		this.xmlName = xmlName;
	}

	public String getXmlName() {
		return xmlName;
	}

	public void setXmlName(String xmlName) {
		this.xmlName = xmlName;
	}

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public boolean isRequired() {
		return required;
	}
	
	public void setRequired(boolean required) {
		this.required = required;
	}
	
	public List<String> getAllowedValues() {
		return allowedValues;
	}
	
	public void setAllowedValues(List<String> allowedValues) {
		this.allowedValues = allowedValues;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getHint() {
		return hint;
	}
	
	public void setHint(String hint) {
		this.hint = hint;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		XMLAttribute other = (XMLAttribute) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "XMLAttribute [value=" + value + ", name=" + name + "]";
	}
	
}
