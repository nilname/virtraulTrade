package com.ooteco.exceptions;

public class SymbolParseException extends Exception{

    public SymbolParseException(String symbol){
        super(symbol + "解析错误");
    }

}
