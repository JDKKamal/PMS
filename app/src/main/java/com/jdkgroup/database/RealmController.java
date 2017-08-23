package com.jdkgroup.database;

import com.jdkgroup.models.CountryModel;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmController {

    private Realm realm;
    public RealmController(Realm realm) {
        this.realm = realm;
    }

    public void addData(CountryModel countryModel) {
      /*  realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                //realm.createObjectFromJson(City.class, "{city: \"Copenhagen\", id: 1 }");

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
            }
        });*/
        realm.beginTransaction();
        realm.copyToRealm(countryModel); // copyToRealmOrUpdate
        realm.commitTransaction();
    }

    public List<CountryModel> getAllData() {
        List<CountryModel> resultdata = new ArrayList<>();
        realm.beginTransaction();
        RealmResults<CountryModel> results = realm.where(CountryModel.class).findAll();
        //results.sort("id", Sort.DESCENDING);
        for (CountryModel person : results) {
            person.setId(person.getId());
           /* person.setFname(person.getFname());
            person.setLname(person.getLname());
            person.setContact(person.getContact());*/

            resultdata.add(person);
        }
        realm.commitTransaction();
        return resultdata;
    }

    public void deleteById(final String id) {
        RealmResults<CountryModel> deleteResult = realm.where(CountryModel.class).equalTo("id", id).findAll();
        realm.beginTransaction();
        deleteResult.deleteAllFromRealm();
        realm.commitTransaction();
    }

    public void updatePerson(long id, String fname, String lname, String contact) {
        realm.beginTransaction();
        CountryModel person = realm.where(CountryModel.class).equalTo("id", id).findFirst();
        /*person.setFname(fname);
        person.setLname(lname);
        person.setContact(contact);
        realm.commitTransaction();*/
    }

    public void deleteAllCountryData()
    {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        });
    }

}
