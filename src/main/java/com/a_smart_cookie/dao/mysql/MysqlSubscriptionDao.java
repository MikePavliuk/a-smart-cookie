package com.a_smart_cookie.dao.mysql;

import com.a_smart_cookie.dao.EntityColumn;
import com.a_smart_cookie.dao.ResourceReleaser;
import com.a_smart_cookie.dao.SubscriptionDao;
import com.a_smart_cookie.entity.Subscription;
import com.a_smart_cookie.exception.DaoException;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object for Subscription related entities implemented with MySql
 *
 */
public class MysqlSubscriptionDao extends SubscriptionDao {

	private static final Logger LOG = Logger.getLogger(MysqlSubscriptionDao.class);

	@Override
	public List<Subscription> getSubscriptionsByUserId(int id) throws DaoException {
		LOG.debug("Starts method");

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = connection.prepareStatement(Query.Subscription.GET_ALL_BY_USER_ID.getQuery());
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();

			LOG.debug("Finished method");
			return extractSubscriptions(rs);

		} catch (SQLException e) {
			LOG.error("Can't get subscriptions by id=" + id, e);
			throw new DaoException("Can't get subscriptions by id=" + id, e);
		} finally {
			ResourceReleaser.close(rs);
			ResourceReleaser.close(pstmt);
		}

	}

	@Override
	public boolean insertSubscription(int userId, int publicationId) throws DaoException {
		LOG.debug("Starts method");

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(Query.Subscription.INSERT.getQuery());
			pstmt.setInt(1, userId);
			pstmt.setInt(2, publicationId);

			LOG.trace(pstmt);

			return pstmt.executeUpdate() > 0;

		} catch (SQLException e) {
			LOG.error("Can't insert subscription", e);
			throw new DaoException("Can't insert subscription", e);
		} finally {
			ResourceReleaser.close(pstmt);
		}
	}

	/**
	 * Extracts subscriptions from ResultSet to List of subscriptions.
	 *
	 * @param rs External ResultSet
	 * @return List of extracted subscriptions
	 */
	private List<Subscription> extractSubscriptions(ResultSet rs) throws SQLException {
		List<Subscription> subscriptions = new ArrayList<>();

		while (rs.next()) {
			subscriptions.add(extractSubscription(rs));
		}

		return subscriptions;
	}

	/**
	 * Method to extract subscription from ResultSet.
	 *
	 * @param rs External ResultSet
	 * @return Extracted subscription
	 */
	private Subscription extractSubscription(ResultSet rs) throws SQLException {
		return new Subscription(
				rs.getInt(EntityColumn.Subscription.PUBLICATION_ID.getName()),
				rs.getDate(EntityColumn.Subscription.START_DATE.getName())
		);
	}


}
