package hnmn3.mechanic.optimist.stockhawk.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by Manish Menaria on 29-Jun-16.
 */
public class HistoryColumns {
    @DataType(DataType.Type.INTEGER) @PrimaryKey
    @AutoIncrement
    public static final String _ID = "_id";
    @DataType(DataType.Type.TEXT) @NotNull
    public static final String SYMBOL = "symbol";
    @DataType(DataType.Type.TEXT)
    public static final String Date = "Date";
    @DataType(DataType.Type.TEXT)
    public static final String Open = "Open";
    @DataType(DataType.Type.TEXT)
    public static final String High = "High";
    @DataType(DataType.Type.TEXT)
    public static final String Low = "Low";
    @DataType(DataType.Type.TEXT)
    public static final String Close = "Close";
    @DataType(DataType.Type.TEXT)
    public static final String Volume = "Volume";
    @DataType(DataType.Type.TEXT)
    public static final String Adj_Close = "Adj_Close";
}
