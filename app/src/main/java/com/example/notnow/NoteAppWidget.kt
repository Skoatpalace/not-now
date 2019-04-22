package com.example.notnow

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

/**
 * Implementation of App Widget functionality.
 */


class NoteAppWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ACTION_NEW_NOTE){

        }
        super.onReceive(context, intent)
    }

    companion object {

        const val ACTION_NEW_NOTE = "com.example.notnow.action.ACTION_NEW_NOTE"

        internal fun updateAppWidget(
            context: Context, appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {

            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.note_app_widget)

            val intentLogo = Intent(context, NoteListActivity::class.java)
            val pendingIntentLogo = PendingIntent.getActivity(context, 0, intentLogo, 0)

            val intentButton = Intent(context, NoteAppWidget::class.java)
            intentButton.action = ACTION_NEW_NOTE
            val pendingButton = PendingIntent.getBroadcast(context, 0, intentButton,0)

            views.setOnClickPendingIntent(R.id.logoWidget, pendingIntentLogo)
            views.setOnClickPendingIntent(R.id.button_new_note, pendingButton)

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

    }
}

