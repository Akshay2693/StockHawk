package hnmn3.mechanic.optimist.stockhawk.graph;

/**
 * Created by Manish Menaria on 02-Jul-16.
 */
public class GraphPoint {
    private String Symbol, Date, Open, High, Low, Close;

    public GraphPoint(String symbol, String date, String open, String high, String low, String close) {
        this.Symbol = symbol;
        this.Date = date;
        this.Open = open;
        this.High = high;
        this.Low = low;
        this.Close = close;
    }


    public String getClose() {
        return Close;
    }

    public String getDate() {
        return Date;
    }

    public String getHigh() {
        return High;
    }

    public String getLow() {
        return Low;
    }

    public String getOpen() {
        return Open;
    }

    public String getSymbol() {
        return Symbol;
    }
}
