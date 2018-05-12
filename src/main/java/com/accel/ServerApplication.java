package com.accel;

import com.accel.endpoints.StreamingEndpoints;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.forms.MultiPartBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ServerApplication extends Application<ReflectionServerConfiguration> {

    public static void main(String[] args) throws Exception {
        new ServerApplication().run(args);
    }


    @Override
    public String getName() {
        return "ReflectionServer";
    }


    @Override
    public void initialize(Bootstrap<ReflectionServerConfiguration> bootstrap) {
        // Enable variable substitution with environment variables
        bootstrap.addBundle(new MultiPartBundle());
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(
                        bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );

        bootstrap.addBundle(new AssetsBundle());
        bootstrap.addBundle(new MigrationsBundle<ReflectionServerConfiguration>() {
            public PooledDataSourceFactory getDataSourceFactory(ReflectionServerConfiguration serverConfiguration) {
                return null;
            }
        });



    }

    public void run(ReflectionServerConfiguration reflectionServerConfiguration, Environment environment) throws Exception {
        environment.jersey().register(new StreamingEndpoints());

    }
}
