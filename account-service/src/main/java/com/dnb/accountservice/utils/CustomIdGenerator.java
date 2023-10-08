package com.dnb.accountservice.utils;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;
import org.hibernate.type.spi.TypeConfiguration;

public class CustomIdGenerator extends SequenceStyleGenerator {
	public static final String VALUE_PREFIX_PARAMETER = "valuePrefix";

	public static final String VALUE_PREFIX_DEFAULT = "";

	private String valuePrefix;

	public static final String NUMBER_FORMAT_PARAMETER = "numberFormat";

	public static final String NUMBER_FORMAT_DEFAULT = "%d";

	private String numberFormat;

	public static final String FLAG_PARAMETER = "flag";

	public static final boolean FLAG_DEFAULT = false;

	private boolean flag;

	public static final String DATE_FORMAT_PARAMETER = "dateFormat";

	public static final String DATE_FORMAT_DEFAULT = "%tY-%tm";

	public static final String DATE_NUMBER_SEPARATOR_PARAMETER = "dateNumberSeparator";

	public static final String DATE_NUMBER_SEPARATOR_DEFAULT = "_";

	private String format;

	@Override

	public Object generate(SharedSessionContractImplementor session, Object object) throws HibernateException

	{

		// TODO Auto-generated method stub

		if (flag)

			return String.format(format, LocalDate.now(), super.generate(session, object));

		else

			return valuePrefix + String.format(numberFormat, super.generate(session, object));

	}

	@Override

	public void configure(Type type, Properties parameters, ServiceRegistry serviceRegistry) throws MappingException //

	{

		// TODO Auto-generated method stub

		System.out.println(parameters);

		super.configure(new TypeConfiguration().getBasicTypeRegistry().getRegisteredType(long.class), parameters,

				serviceRegistry);

		flag = ConfigurationHelper.getBooleanWrapper(FLAG_PARAMETER, parameters, FLAG_DEFAULT);

		if (flag) {

			valuePrefix = ConfigurationHelper.getString(VALUE_PREFIX_PARAMETER, parameters, VALUE_PREFIX_DEFAULT);

			String dateFormat = ConfigurationHelper.getString(DATE_FORMAT_PARAMETER, parameters, DATE_FORMAT_DEFAULT)

					.replace("%", "%1");

			numberFormat = ConfigurationHelper

					.getString(NUMBER_FORMAT_PARAMETER, parameters, NUMBER_FORMAT_DEFAULT).replace("%", "");

			String dateNumberSeparator = ConfigurationHelper.getString(DATE_NUMBER_SEPARATOR_PARAMETER, parameters,

					DATE_NUMBER_SEPARATOR_DEFAULT);

			this.format = valuePrefix + dateFormat + dateNumberSeparator + numberFormat;

		} else {

			valuePrefix = ConfigurationHelper.getString(VALUE_PREFIX_PARAMETER, parameters, VALUE_PREFIX_DEFAULT);

			numberFormat = ConfigurationHelper.getString(NUMBER_FORMAT_PARAMETER, parameters, NUMBER_FORMAT_DEFAULT);

		}

	}

}
