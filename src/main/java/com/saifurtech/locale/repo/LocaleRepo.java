package com.saifurtech.locale.repo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.saifurtech.locale.dao.entity.Location;
import com.saifurtech.locale.dao.entity.ZipCodesByCountryCityAndState;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableScan
@Repository
public class LocaleRepo {
    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public List<Location> findAllByZipCode(String zipCode) {
        Location location = new Location();
        location.setPostalCode(zipCode);

        DynamoDBQueryExpression<Location> queryExpression = new DynamoDBQueryExpression<Location>()
                .withHashKeyValues(location);

        List<Location> query = dynamoDBMapper.query(Location.class, queryExpression);

        return query;
    }

    public <T> void save(T entity) {
        dynamoDBMapper.save(entity);
    }

    public ZipCodesByCountryCityAndState findAllZipByCountryStateAndCity(String country, String state, String city) {
        ZipCodesByCountryCityAndState zipCodes = new ZipCodesByCountryCityAndState();
        zipCodes.setCountry(country);
        zipCodes.setState(state);
        zipCodes.setCity(city);

        DynamoDBQueryExpression<ZipCodesByCountryCityAndState> queryExpression
                = new DynamoDBQueryExpression<ZipCodesByCountryCityAndState>()
                .withHashKeyValues(zipCodes);

        QueryResultPage<ZipCodesByCountryCityAndState> query = dynamoDBMapper
                .queryPage(ZipCodesByCountryCityAndState.class, queryExpression);

        return query.getResults().isEmpty() ? null : query.getResults().get(0);
    }
}
