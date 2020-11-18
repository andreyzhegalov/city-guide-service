package cityguide.datastorage.mongo.dao;

class DaoMongoException extends RuntimeException{
    DaoMongoException(){
    }

    DaoMongoException(String msg){
        super(msg);
    }
}

