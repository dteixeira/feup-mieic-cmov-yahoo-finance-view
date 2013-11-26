package com.example.stockviewer;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class StockListAdapter extends BaseAdapter {
	
	private LayoutInflater inflater = null;
	private Context context = null;
	private ArrayList<Stock> stocks = null;
	
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
		view = view != null ? view : inflater.inflate(R.layout.stock_list_row, null);
		return view;
	}

	@Override
	public long getItemId(int index) {
		return index;
	}
	
	public void removeItem(int index) {
		
	}

}
