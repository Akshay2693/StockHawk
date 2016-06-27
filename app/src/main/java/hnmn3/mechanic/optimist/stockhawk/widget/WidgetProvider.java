package hnmn3.mechanic.optimist.stockhawk.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

import hnmn3.mechanic.optimist.stockhawk.R;
import hnmn3.mechanic.optimist.stockhawk.graph.GraphActivity;

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


            Intent intentStockGraph = new Intent(context, GraphActivity.class);
            PendingIntent pendingIntent = TaskStackBuilder.create(context)
                    .addNextIntentWithParentStack(intentStockGraph)
                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setPendingIntentTemplate(R.id.widget_list_view,pendingIntent);

            // Update Widget on HomeScreen
            appWidgetManager.updateAppWidget(widgetId,views);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}
