package com.benxiaopao.provider.dao.model;

public class Menu {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column menu.menuId
     *
     * @mbg.generated Wed Apr 03 23:31:44 CST 2019
     */
    private Integer menuId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column menu.parentId
     *
     * @mbg.generated Wed Apr 03 23:31:44 CST 2019
     */
    private Integer parentId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column menu.menuName
     *
     * @mbg.generated Wed Apr 03 23:31:44 CST 2019
     */
    private String menuName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column menu.showOrder
     *
     * @mbg.generated Wed Apr 03 23:31:44 CST 2019
     */
    private Integer showOrder;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column menu.url
     *
     * @mbg.generated Wed Apr 03 23:31:44 CST 2019
     */
    private String url;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column menu.isVisible
     *
     * @mbg.generated Wed Apr 03 23:31:44 CST 2019
     */
    private Byte isVisible;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column menu.menuId
     *
     * @return the value of menu.menuId
     *
     * @mbg.generated Wed Apr 03 23:31:44 CST 2019
     */
    public Integer getMenuId() {
        return menuId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column menu.menuId
     *
     * @param menuId the value for menu.menuId
     *
     * @mbg.generated Wed Apr 03 23:31:44 CST 2019
     */
    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column menu.parentId
     *
     * @return the value of menu.parentId
     *
     * @mbg.generated Wed Apr 03 23:31:44 CST 2019
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column menu.parentId
     *
     * @param parentId the value for menu.parentId
     *
     * @mbg.generated Wed Apr 03 23:31:44 CST 2019
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column menu.menuName
     *
     * @return the value of menu.menuName
     *
     * @mbg.generated Wed Apr 03 23:31:44 CST 2019
     */
    public String getMenuName() {
        return menuName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column menu.menuName
     *
     * @param menuName the value for menu.menuName
     *
     * @mbg.generated Wed Apr 03 23:31:44 CST 2019
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column menu.showOrder
     *
     * @return the value of menu.showOrder
     *
     * @mbg.generated Wed Apr 03 23:31:44 CST 2019
     */
    public Integer getShowOrder() {
        return showOrder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column menu.showOrder
     *
     * @param showOrder the value for menu.showOrder
     *
     * @mbg.generated Wed Apr 03 23:31:44 CST 2019
     */
    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column menu.url
     *
     * @return the value of menu.url
     *
     * @mbg.generated Wed Apr 03 23:31:44 CST 2019
     */
    public String getUrl() {
        return url;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column menu.url
     *
     * @param url the value for menu.url
     *
     * @mbg.generated Wed Apr 03 23:31:44 CST 2019
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column menu.isVisible
     *
     * @return the value of menu.isVisible
     *
     * @mbg.generated Wed Apr 03 23:31:44 CST 2019
     */
    public Byte getIsVisible() {
        return isVisible;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column menu.isVisible
     *
     * @param isVisible the value for menu.isVisible
     *
     * @mbg.generated Wed Apr 03 23:31:44 CST 2019
     */
    public void setIsVisible(Byte isVisible) {
        this.isVisible = isVisible;
    }
}