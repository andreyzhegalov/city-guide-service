package cityguide.datastorage.mongo.db;

import org.jetbrains.annotations.NotNull;
import org.testcontainers.containers.GenericContainer;

public class MongoDbContainer extends GenericContainer<MongoDbContainer> {

    /**
     * This is the internal port on which MongoDB is running inside the container.
     * <p>
     * You can use this constant in case you want to map an explicit public port to it
     * instead of the default random port. This can be done using methods like
     * {@link #setPortBindings(java.util.List)}.
     */
    public static final int MONGODB_PORT = 27017;
    public static final String DEFAULT_IMAGE_AND_TAG = "mongo:4.4.1";

    public MongoDbContainer() {
        this(DEFAULT_IMAGE_AND_TAG);
    }

    public MongoDbContainer(@NotNull String image) {
        super(image);
        addExposedPort(MONGODB_PORT);
    }

    public Integer getPort() {
        return getMappedPort(MONGODB_PORT);
    }
}

