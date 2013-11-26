package com.example.stockviewer;

import android.content.Context;
import android.content.Intent;
import android.widget.ListView;

import com.example.stockviewer.SwipeDismissListViewTouchListener.DismissCallbacks;

public class StockListSwipeDismissCallbacks implements DismissCallbacks {
	
	private Context context = null;
	private UndoBarController mUndoBarController = null;
	
	public StockListSwipeDismissCallbacks(Context context, UndoBarController mUndoBarController) {
		super();
		this.context = context;
		this.mUndoBarController = mUndoBarController;
	}

	@Override
	public boolean canDismiss(int position) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void onDismiss(ListView listView, int[] reverseSortedPositions) {
		int position = reverseSortedPositions.length > 0 ? reverseSortedPositions[0] : -1;
		Intent intent = new Intent();
		intent.putExtra(StockListAdapter.EXTRA_STOCK_LIST_REMOVE_POSITION, position);
		((StockListAdapter) listView.getAdapter()).removeItem(position);
		mUndoBarController.hideUndoBar(true);
		mUndoBarController.showUndoBar(false, context.getString(R.string.undo_stock_remove), intent);
	}

}