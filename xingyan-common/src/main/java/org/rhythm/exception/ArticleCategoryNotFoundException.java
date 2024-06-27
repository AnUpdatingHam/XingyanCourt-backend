package org.rhythm.exception;

/**
 * 文章种类未找到异常
 */
public class ArticleCategoryNotFoundException extends BaseException {

    public ArticleCategoryNotFoundException() {
    }

    public ArticleCategoryNotFoundException(String msg) {
        super(msg);
    }

}
