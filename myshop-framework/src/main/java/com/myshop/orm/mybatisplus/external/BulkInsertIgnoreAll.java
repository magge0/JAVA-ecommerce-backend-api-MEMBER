package com.myshop.orm.mybatisplus.external;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author vantrang
 */
public class BulkInsertIgnoreAll extends AbstractMethod {

    private static final String MAPPER_METHOD = "insertIgnoreBatchAllColumn";
    private static final long serialVersionUID = 9014932405041929700L;

    @Setter
    @Accessors(chain = true)
    private Predicate<TableFieldInfo> predicate;

    protected BulkInsertIgnoreAll(String methodName) {
        super(methodName);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        // Khởi tạo KeyGenerator là NoKeyGenerator (không tạo khóa)
        KeyGenerator keyGenerator = new NoKeyGenerator();
        // Xác định phương thức SQL là INSERT_ONE
        SqlMethod sqlMethod = SqlMethod.INSERT_ONE;
        // Mẫu SQL cho lệnh INSERT
        String sqlTemplate = "<script>\nINSERT IGNORE INTO %s %s VALUES %s\n</script>";

        // Lấy danh sách các thuộc tính của bảng
        List<TableFieldInfo> fieldList = tableInfo.getFieldList();
        // Lấy danh sách các cột cho lệnh INSERT
        String insertSqlColumn = tableInfo.getKeyInsertSqlColumn(true,"", false) + this.filterTableFieldInfo(fieldList, predicate, TableFieldInfo::getInsertSqlColumn, EMPTY);
        // Định dạng danh sách cột thành chuỗi SQL
        String columnScript = LEFT_BRACKET + insertSqlColumn.substring(0, insertSqlColumn.length() - 1) + RIGHT_BRACKET;
        // Lấy danh sách các thuộc tính cho lệnh INSERT
        String insertSqlProperty = tableInfo.getKeyInsertSqlProperty(true, ENTITY_DOT, false) + this.filterTableFieldInfo(fieldList, predicate, i -> i.getInsertSqlProperty(ENTITY_DOT), EMPTY);
        // Định dạng danh sách thuộc tính thành chuỗi SQL
        insertSqlProperty = LEFT_BRACKET + insertSqlProperty.substring(0, insertSqlProperty.length() - 1) + RIGHT_BRACKET;
        // Tạo đoạn mã SQL cho phần VALUES của lệnh INSERT
        String valuesScript = SqlScriptUtils.convertForeach(insertSqlProperty, "list", null, ENTITY, COMMA);
        // Khai báo biến keyProperty để lưu trữ tên thuộc tính khóa chính
        String keyProperty = null;
        // Khai báo biến keyColumn để lưu trữ tên cột khóa chính
        String keyColumn = null;
        // Bảng chứa logic xử lý khóa chính. Nếu không chứa khóa chính, nó sẽ được coi là một trường bình thường.
        if (StringUtils.isNotEmpty(tableInfo.getKeyProperty())) {
            // Nếu khóa chính là tự động tăng
            if (tableInfo.getIdType() == IdType.AUTO) {
                /* khóa chính tự động tăng */
                keyGenerator = new Jdbc3KeyGenerator();
                // Lấy tên thuộc tính khóa chính
                keyProperty = tableInfo.getKeyProperty();
                // Lấy tên cột khóa chính
                keyColumn = tableInfo.getKeyColumn();
            } else {
                if (null != tableInfo.getKeySequence()) {
                    // Tạo KeyGenerator dựa trên sequence
                    keyGenerator = TableInfoHelper.genKeyGenerator(sqlMethod.getMethod(), tableInfo, builderAssistant);
                    // Lấy tên thuộc tính khóa chính
                    keyProperty = tableInfo.getKeyProperty();
                    // Lấy tên cột khóa chính
                    keyColumn = tableInfo.getKeyColumn();
                }
            }
        }
        // Tạo chuỗi SQL đầy đủ cho lệnh INSERT
        String sql = String.format(sqlTemplate, tableInfo.getTableName(), columnScript, valuesScript);
        // Tạo SqlSource từ chuỗi SQL
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        // Thêm lệnh INSERT vào MappedStatement và trả về
        return this.addInsertMappedStatement(mapperClass, modelClass, MAPPER_METHOD, sqlSource, keyGenerator, keyProperty, keyColumn);
    }
}