
package fawda.java.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * anonymous complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="in0" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="in1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="in2" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="in3" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="in4" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="in5" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="in6" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="in7" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="in8" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="in9" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="in10" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="in11" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "in0", "in1", "in2", "in3", "in4", "in5", "in6", "in7", "in8", "in9", "in10", "in11" })
@XmlRootElement(name = "singleFileSend")
public class SingleFileSend {

    @XmlElement(required = true, nillable = true)
    protected String in0;
    @XmlElement(required = true, nillable = true)
    protected String in1;
    @XmlElement(required = true, nillable = true)
    protected String in2;
    protected int in3;
    @XmlElement(required = true, nillable = true)
    protected String in4;
    @XmlElement(required = true, nillable = true)
    protected String in5;
    protected int in6;
    @XmlElement(required = true, nillable = true)
    protected String in7;
    @XmlElement(required = true, nillable = true)
    protected String in8;
    @XmlElement(required = true, nillable = true)
    protected String in9;
    @XmlElement(required = true, nillable = true)
    protected String in10;
    @XmlElement(required = true, nillable = true)
    protected String in11;

    /**
     * 获取in0属性的值。
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getIn0() {
        return in0;
    }

    /**
     * 设置in0属性的值。
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setIn0(String value) {
        this.in0 = value;
    }

    /**
     * 获取in1属性的值。
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getIn1() {
        return in1;
    }

    /**
     * 设置in1属性的值。
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setIn1(String value) {
        this.in1 = value;
    }

    /**
     * 获取in2属性的值。
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getIn2() {
        return in2;
    }

    /**
     * 设置in2属性的值。
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setIn2(String value) {
        this.in2 = value;
    }

    /**
     * 获取in3属性的值。
     * 
     */
    public int getIn3() {
        return in3;
    }

    /**
     * 设置in3属性的值。
     * 
     */
    public void setIn3(int value) {
        this.in3 = value;
    }

    /**
     * 获取in4属性的值。
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getIn4() {
        return in4;
    }

    /**
     * 设置in4属性的值。
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setIn4(String value) {
        this.in4 = value;
    }

    /**
     * 获取in5属性的值。
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getIn5() {
        return in5;
    }

    /**
     * 设置in5属性的值。
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setIn5(String value) {
        this.in5 = value;
    }

    /**
     * 获取in6属性的值。
     * 
     */
    public int getIn6() {
        return in6;
    }

    /**
     * 设置in6属性的值。
     * 
     */
    public void setIn6(int value) {
        this.in6 = value;
    }

    /**
     * 获取in7属性的值。
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getIn7() {
        return in7;
    }

    /**
     * 设置in7属性的值。
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setIn7(String value) {
        this.in7 = value;
    }

    /**
     * 获取in8属性的值。
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getIn8() {
        return in8;
    }

    /**
     * 设置in8属性的值。
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setIn8(String value) {
        this.in8 = value;
    }

    /**
     * 获取in9属性的值。
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getIn9() {
        return in9;
    }

    /**
     * 设置in9属性的值。
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setIn9(String value) {
        this.in9 = value;
    }

    /**
     * 获取in10属性的值。
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getIn10() {
        return in10;
    }

    /**
     * 设置in10属性的值。
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setIn10(String value) {
        this.in10 = value;
    }

    /**
     * 获取in11属性的值。
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getIn11() {
        return in11;
    }

    /**
     * 设置in11属性的值。
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setIn11(String value) {
        this.in11 = value;
    }

}
