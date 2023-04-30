package niuniu.javaweb.utils.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.utils.tools
 * @date 2022/12/29 16:48
 */
@Component
public class SeqTools
{
    private static JdbcTemplate jdbcTemplate = null;

    private static String baseCode = "0000";

    @Autowired
    public void SetDataSource(DataSource dataSource)
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public static String getNumber(String seqName) throws Exception
    {
        String sql1 = """
				select x.sval
				  from sequence x
				 where x.sdate=CURRENT_DATE
				   and x.sname=?
				""";
        int curr_sval=jdbcTemplate.query(sql1, new ResultSetExtractor<Integer>()
                {
                    @Override
                    public Integer extractData(ResultSet rs) throws SQLException, DataAccessException
                    {
                        int result = 0;
                        if(rs.next())
                        {
                            result=rs.getInt(1);
                        }
                        return result;
                    }
                }
                , seqName);
        String sql2 =null;
        if(curr_sval==0)
        {
            sql2 = """
					insert into sequence(sval,sname,sdate)
                                  values(?,?,current_date)
					""";
        }
        else {
            sql2 = """
					update sequence
					   set sval=?
					 where sname=?
					   and sdate= current_date
					""";
        }

        Object params[]= {++curr_sval,seqName};

        jdbcTemplate.update(sql2, params);

        int with = String.valueOf(curr_sval).length();

        String Number = LocalDate.now().toString().replace("-","") + baseCode.substring(with) + curr_sval;

        return Number;
    }

}
