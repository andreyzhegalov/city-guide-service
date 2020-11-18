package cityguide.datastorage.mongo.db;

class MongoDbControllerException extends RuntimeException {
    public MongoDbControllerException() {
    }

    public MongoDbControllerException(String msg) {
        super(msg);
    }
}

