package hnmn3.mechanic.optimist.stockhawk.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import hnmn3.mechanic.optimist.stockhawk.R;

/**
 * Created by Manish Menaria on 27-Jun-16.
 */
public class WidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // update all StockWidgets
        for (int widgetId: appWidgetIds){
            Intent intent = new Intent(context,StockWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,widgetId);

            // create Widget
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            views.setRemoteAdapter(R.id.widget_list_view,intent);


            // The empty view is displayed when the collection has no items.
            views.setEmptyView(R.id.widget_list_view,R.id.empty_view);

            // Here we setup the a pending intent template. Individuals items of a collection
            // cannot setup their own pending intents, instead, the collection as a whole can
            // setup a pending intent template, and the individual items can set a fillInIntent
            // to create unique before on an item to item basis.
            Intent intentStockGraph = new Intent(context, WidgetProvider.class);
            intentStockGraph.setAction("hnmn3.mechanic.optimist.stockhawk.widget.TOAST");
            intentStockGraph.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds);
            intent.setData(Uri.parse(intent.toUri(intent.URI_INTENT_SCHEME)));
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intentStockGraph,PendingIntent.FLAG_UPDATE_CURRENT);

            // Update Widget on HomeScreen
            appWidgetManager.updateAppWidget(widgetId,views);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager mgr = AppWidgetManager.getInstance(context);
        if (intent.getAction().equals("hnmn3.mechanic.optimist.stockhawk.widget.TOAST")) {
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            int viewIndex = intent.getIntExtra("hnmn3.mechanic.optimist.stockhawk.widget.ExtraItem", 0);
            Toast.makeText(context, "Touched view " + viewIndex, Toast.LENGTH_SHORT).show();
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

}
