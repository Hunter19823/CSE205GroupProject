package shopping;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

public class Catalogue<TYPE> {
    private static final Logger LOGGER = LogManager.getLogger(Catalogue.class);
    private final int ITEMS_PER_PAGE;
    private List<List<TYPE>> pages;
    private int currentPage;

    public Catalogue( int items_per_page )
    {
        ITEMS_PER_PAGE = items_per_page;
        currentPage = 0;
    }

    public boolean isCurrentPageFull()
    {
        return false;
    }

    public boolean isPageFull(int page)
    {
        return false;
    }

    public boolean nextPage()
    {
        return false;
    }

    public boolean previousPage()
    {
        return false;
    }

    public boolean firstPage()
    {
        return false;
    }

    public boolean lastPage()
    {
        return false;
    }

    public int pageCount()
    {
        return 0;
    }

    public int itemsOnPage(int page)
    {
        return 0;
    }

    public boolean isValidPage(boolean page)
    {
        return false;
    }

    public int addToCatalogue(List<TYPE> items)
    {
        return 0;
    }

}
