package com.example.realm1;

import android.util.Log;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

public class Migration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();

        /*
        Version 0

        class Persona
        @PrimaryKey
        private String dni;
        private String name;
        private String surname;
        private int age;
        private String gender;


        Version 1

        class Persona
        @PrimaryKey
        private String dni;
        @Index @Required
        private String fullName;
        private int age;
        private String gender;
        @Required
        private String email;

        */

        if (oldVersion == 0) {
            Log.d("Migration", "Upgrading DB version to 1.0");

            RealmObjectSchema objectSchema = schema.get("Persona");

            objectSchema
                    .addField("fullName", String.class, FieldAttribute.REQUIRED)
                    .transform(new RealmObjectSchema.Function() {
                        @Override
                        public void apply(DynamicRealmObject obj) {
                            //Empty space
                            obj.set("fullName", obj.getString("name") + " " + obj.getString("surname"));
                        }
                    })
                    .addIndex("fullName")
                    .addField("email", String.class, FieldAttribute.REQUIRED)
                    .removeField("name");

            // This makes the IF just run once.
            oldVersion++;
        }
    }
}
