package com.hlwd.validation.intercepts;


import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
@Intercepts({@Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
), @Signature(
        type = Executor.class,
        method = "update",
        args = {MappedStatement.class, Object.class}
)})
public class SqlInterceptor implements Interceptor {
    private static Logger logger = LoggerFactory.getLogger(SqlInterceptor.class);

    public SqlInterceptor() {
    }
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement)invocation.getArgs()[0];
        Object parameterObject = null;
        if (invocation.getArgs().length > 1) {
            parameterObject = invocation.getArgs()[1];
        }

        String statementId = mappedStatement.getId();
        BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);
        Configuration configuration = mappedStatement.getConfiguration();
        String sql = MybatisUtil.getSql(boundSql, parameterObject, configuration);
        Object result = null;

        try {
            long start = System.currentTimeMillis();
            result = invocation.proceed();
            long end = System.currentTimeMillis();
            long timing = end - start;
            logger.info(String.format("[耗时：%d ms]-%s\r\n%s", timing, statementId, sql) + "\r\n结果：" + result);
            return result;
        } catch (InvocationTargetException var15) {
            logger.error(String.format("[执行SQL异常]-%s：%s", statementId, sql) + "\r\n" + var15.getTargetException().getMessage());
            throw var15;
        }
    }


    public Object plugin(Object target) {
        return target instanceof Executor ? Plugin.wrap(target, this) : target;
    }

    public void setProperties(Properties properties) {
    }
}
