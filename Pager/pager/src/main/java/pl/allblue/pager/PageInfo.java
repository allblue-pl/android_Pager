package pl.allblue.pager;

public class PageInfo
{

    private String name = null;
    private String tag = null;
    private Page page = null;

    public PageInfo(String pager_tag, String name, Page page)
    {
        this.name = name;
        this.tag = pager_tag + "." + name;
        this.page = page;
    }

    public Page getPage()
    {
        return this.page;
    }

    public String getName()
    {
        return this.name;
    }

    public String getTag()
    {
        return this.tag;
    }

}
