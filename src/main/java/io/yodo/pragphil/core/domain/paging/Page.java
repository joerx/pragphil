package io.yodo.pragphil.core.domain.paging;

import java.util.List;

// used in JSPs, but IntelliJ lost track of model-to-view mappings, so screw this
@SuppressWarnings({"unused", "WeakerAccess"})
public class Page<E> {

    private final List<E> contents;

    private final long totalRecords;

    private final int currentPage;

    private final int recordsPerPage;

    public Page(List<E> contents, long totalRecords, int currentPage, int recordsPerPage) {
        this.contents = contents;
        this.totalRecords = totalRecords;
        this.currentPage = currentPage;
        this.recordsPerPage = recordsPerPage;
    }

    public List<E> getContents() {
        return contents;
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public int getNumPages() {
        return (int) Math.ceil((double) totalRecords/(double) recordsPerPage);
    }

    public boolean hasPreviousPage() {
        return currentPage > 1;
    }

    public int getPreviousPage() {
        if (!hasPreviousPage()) {
            throw new IndexOutOfBoundsException("No previous page for page " + currentPage);
        }
        return currentPage - 1;
    }

    public boolean hasNextPage() {
        return currentPage < getNumPages();
    }

    public int getNextPage() {
        if (!hasNextPage()) {
            throw new IndexOutOfBoundsException("No previous page for page " + currentPage);
        }
        return currentPage + 1;
    }

    public boolean needsPaging() {
        return getNumPages() > 1;
    }

    public java.util.Iterator<Page.PagerItem> pageIterator() {
        return this.new Iterator();
    }

    public class PagerItem {

        private int number;

        public int getNumber() {
            return number;
        }

        public boolean isCurrent() {
            return number == currentPage;
        }
    }

    class Iterator implements java.util.Iterator<Page.PagerItem> {

        private final PagerItem item = Page.this.new PagerItem();

        @Override
        public boolean hasNext() {
            return item.getNumber() < getNumPages();
        }

        @Override
        public PagerItem next() {
            if (!hasNext()) {
                throw new IndexOutOfBoundsException("No page after" + item.getNumber());
            }
            item.number += 1;
            return item;
        }
    }
}
