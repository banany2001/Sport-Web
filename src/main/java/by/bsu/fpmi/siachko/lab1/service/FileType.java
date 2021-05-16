package by.bsu.fpmi.siachko.lab1.service;

public enum FileType {

    XML, JSON, CSV;

    @Override
    public String toString() {
        if (this == XML){
            return ".xml";
        }
        else if (this == JSON){
            return ".json";
        }
        else if (this == CSV){
            return ".csv";
        }
        else {
            return ".txt";
        }
    }
}
