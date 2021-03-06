package gean.pmc_report_generator.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import gean.pmc_report_generator.dao.GeneratorDao;
import gean.pmc_report_generator.dao.MySQLGeneratorDao;
import gean.pmc_report_generator.dao.OracleGeneratorDao;
import gean.pmc_report_generator.dao.PostgreSQLGeneratorDao;
import gean.pmc_report_generator.dao.SQLServerGeneratorDao;
import gean.pmc_report_generator.utils.ReportException;

/**
 * 数据库配置
 *
 */
@Configuration
public class DbConfig {
    @Value("${gean.database: oracle}")
    private String database;
    @Autowired
    private MySQLGeneratorDao mySQLGeneratorDao;
    @Autowired
    private OracleGeneratorDao oracleGeneratorDao;
    @Autowired
    private SQLServerGeneratorDao sqlServerGeneratorDao;
    @Autowired
    private PostgreSQLGeneratorDao postgreSQLGeneratorDao;

    @Bean
    @Primary
    public GeneratorDao getGeneratorDao(){
        if("mysql".equalsIgnoreCase(database)){
            return mySQLGeneratorDao;
        }else if("oracle".equalsIgnoreCase(database)){
            return oracleGeneratorDao;
        }else if("sqlserver".equalsIgnoreCase(database)){
            return sqlServerGeneratorDao;
        }else if("postgresql".equalsIgnoreCase(database)){
            return postgreSQLGeneratorDao;
        }else {
            throw new ReportException("不支持当前数据库：" + database);
        }
    }
}
