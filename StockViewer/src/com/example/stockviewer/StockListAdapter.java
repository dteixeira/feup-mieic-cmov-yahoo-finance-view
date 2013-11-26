package com.example.stockviewer;

import java.util.ArrayList;
import com.example.stockviewer.UndoBarController.UndoListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class StockListAdapter extends BaseAdapter implements UndoListener {

	public static final String EXTRA_STOCK_LIST_REMOVE_POSITION = "StockListRemovePosition";
	public static final String SAVED_UNDO_INDEX = "UndoIndex";
	public static final String SAVED_UNDO_STOCK = "UndoStock";
	public static final String SAVED_STOCKS = "Stocks";
	
	private LayoutInflater inflater = null;
	private Context context = null;
	private ArrayList<Stock> stocks = null;
	private Stock undoStock = null;
	private int undoIndex = 0;
	
	public StockListAdapter(Context context, ArrayList<Stock> stocks) {
		this.context = context;
		this.stocks = stocks;
		this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return stocks == null ? 0 : stocks.size();
	}

	@Override
	public Object getItem(int index) {
		return stocks == null ? null : stocks.get(index);
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		// TODO SET VALUES, SENSITIVE CONTENT
		Stock stock = stocks.get(position);
		view = view != null ? view : inflater.inflate(R.layout.stock_list_row, null);
		
		// TODO REMOVE
		((TextView)view.findViewById(R.id.dummy_content)).setText(stock.dummy + "");
		return view;
	}

	@Override
	public long getItemId(int index) {
		return index;
	}

	@Override
	public void onUndo(Parcelable token) {
		int position = token == null ? -1 : ((Intent) token).getIntExtra(EXTRA_STOCK_LIST_REMOVE_POSITION, -1);
		
		// Valid undo command.
		if(position == undoIndex) {
			stocks.add(undoIndex, undoStock);
			undoIndex = -1;
			undoStock = null;
			notifyDataSetChanged();
		}
	}
	
	public void removeItem(int index) {
		if(index >= 0 && index < stocks.size()) {
			undoIndex = index;
			undoStock = stocks.remove(index);
			notifyDataSetChanged();
		}
	}
	
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt(SAVED_UNDO_INDEX, undoIndex);
		outState.putSerializable(SAVED_UNDO_STOCK, undoStock);
		outState.putSerializable(SAVED_STOCKS, stocks);
	}
	
	@SuppressWarnings("unchecked")
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		if(savedInstanceState != null) {
			undoIndex = savedInstanceState.getInt(SAVED_UNDO_INDEX);
			undoStock = (Stock) savedInstanceState.getSerializable(SAVED_UNDO_STOCK);
			stocks = (ArrayList<Stock>) savedInstanceState.getSerializable(SAVED_STOCKS);
		}
	}

}