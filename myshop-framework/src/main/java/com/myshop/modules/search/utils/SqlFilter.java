package com.myshop.modules.search.utils;

import com.myshop.common.utils.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Lọc từ khóa SQL
 */
public class SqlFilter {
    // Mẫu lọc SQL Injection
    static final String SQL_INJECTION_KEYWORDS_PATTERN = "(?i)(SELECT|FROM|WHERE|WHEN|CONCAT|AND|NOT|INSERT|UPDATE|DELETE" + "|HAVING|ORDER|ASC|DESC|LIKE|IN|ELSE|BETWEEN|IS|NULL|TRUE|FALSE" + "|JOIN|LEFT|RIGHT|UNION|INNER|OUTER|FULL|ON|AS|DISTINCT|COUNT" + "|MAX|MIN|SUM|AVG|IF|RAND|UPDATEXML|EXTRACTVALUE|LOAD_FILE|SLEEP|OFFSET|CASE|THEN|END)";
    // OR ảnh hưởng đến trường sắp xếp sort, do đó tạm thời không lọc
    // CREATE ảnh hưởng đến trường sắp xếp phổ biến, CREATE_TIME, do đó tạm thời không lọc
    static final Pattern sqlKeywordPattern = Pattern.compile(SQL_INJECTION_KEYWORDS_PATTERN, Pattern.CASE_INSENSITIVE);


    /**
     * Từ khóa trùng khớp
     *
     * @param sql
     * @return
     */
    public static Boolean checkSqlKeywords(String sql) {
        if (StringUtils.isEmpty(sql)) {
            return false;
        }
        Matcher matcher = sqlKeywordPattern.matcher(sql);
        return matcher.find();
    }
}