package hnmn3.mechanic.optimist.stockhawk.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import hnmn3.mechanic.optimist.stockhawk.R;
import hnmn3.mechanic.optimist.stockhawk.data.QuoteColumns;
import hnmn3.mechanic.optimist.stockhawk.data.QuoteProvider;

/**
 * Created by Manish Menaria on 27-Jun-16.
 */
public class StockWidgetService extends RemoteViewsService {

    public StockWidgetService() {
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetItemRemoteView(this.getApplicationContext(), intent);
    }
}

class WidgetItemRemoteView implements RemoteViewsService.RemoteViewsFactory {
    Context context;
    Cursor cursor;
    Intent mIntent;

    public WidgetItemRemoteView(Context mContext, Intent mIntent) {
        this.context = mContext;
        this.mIntent = mIntent;
    }

    @Override
    public void onCreate() {
        if (cursor != null)
            cursor.close();

        final long pId = Binder.clearCallingIdentity();

        cursor = context.getContentResolver().query(
                QuoteProvider.Quotes.CONTENT_URI,
                null,
                QuoteColumns.ISCURRENT + " = ?",
                new String[]{"1"},
                null
        );
        Binder.restoreCallingIdentity(pId);
    }

    @Override
    public void onDataSetChanged() {
        if (cursor != null)
            cursor.close();

        final long pId = Binder.clearCallingIdentity();

        cursor = context.getContentResolver().query(
                QuoteProvider.Quotes.CONTENT_URI,
                null,
                QuoteColumns.ISCURRENT + " = ?",
                new String[]{"1"},
                null
        );
        Binder.restoreCallingIdentity(pId);
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews listItemRemoteView = null;
        try {
            cursor.moveToPosition(position);
            int priceChangeColorId;

            // get Stock Quote information
            String stockSymbol = cursor.getString(cursor.getColumnIndex(QuoteColumns.SYMBOL));
            String stockBidPrice = cursor.getString(cursor.getColumnIndex(QuoteColumns.BIDPRICE));
            String stockPriceChange = cursor.getString(cursor.getColumnIndex(QuoteColumns.CHANGE));
            int isUp = cursor.getInt(cursor.getColumnIndex(QuoteColumns.ISUP));

            // create List Item for Widget ListView
            listItemRemoteView = new RemoteViews(context.getPackageName(), R.layout.widget_list_item);
            listItemRemoteView.setTextViewText(R.id.stock_symbol, stockSymbol + "");
            listItemRemoteView.setTextViewText(R.id.bid_price, stockBidPrice + "");
            listItemRemoteView.setTextViewText(R.id.change, stockPriceChange + "");

            // if stock price is Up then background of price Change is Green else Red
            if (isUp == 1)
                priceChangeColorId = R.drawable.percent_change_pill_green;
            else
                priceChangeColorId = R.drawable.percent_change_pill_red;
            listItemRemoteView.setInt(R.id.change, "setBackgroundResource", priceChangeColorId);

            // set Onclick Item Intent
            Intent onClickItemIntent = new Intent();
            onClickItemIntent.putExtra("clicked_position", position + "");
            listItemRemoteView.setOnClickFillInIntent(R.id.list_item_stock_quote, onClickItemIntent);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return listItemRemoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public void onDestroy() {
        if (cursor != null)
            cursor.close();
    }

    @Override
    public int getCount() {
        return 0;
    }

}

