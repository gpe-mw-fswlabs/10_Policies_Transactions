package org.acme.insurance;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.naming.InitialContext;
import javax.sql.DataSource;

@Named("dbcontroller")
@RequestScoped
public class DbController {

	private List<PolicyQuote> results;
	
	public DbController() {
		results = new ArrayList<PolicyQuote>();
	}

	public void executeQuery(String query) {
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:jboss/datasources/mysqlDS");
			
			conn = ds.getConnection();

			st = conn.createStatement();
			rs = st.executeQuery(query);

			while (rs.next()) {
				PolicyQuote quote = new PolicyQuote();
				quote.setPolicyQuoteID(rs.getLong("id"));
				quote.setAge(rs.getInt("age"));
				quote.setCreditScore(rs.getInt("credit_score"));
				quote.setDlNumber(rs.getString("dl_number"));
				quote.setDriverName(rs.getString("driver_name"));
				quote.setNumberOfAccidents(rs.getInt("num_of_accidents"));
				quote.setNumberOfTickets(rs.getInt("num_of_tickets"));
				quote.setPolicyType(rs.getString("policy_type"));
				quote.setPrice(rs.getInt("price"));
				quote.setRequestDate(rs.getDate("request_date"));
				quote.setSsn(rs.getString("ssn"));
				quote.setVehicleYear(rs.getInt("vehicle_year"));
				
				results.add(quote);
			}
		} catch (Exception e) {
				e.printStackTrace();
		}
		
	}
	
	public List<PolicyQuote> getResults() {
		return this.results;
	}
	
}
