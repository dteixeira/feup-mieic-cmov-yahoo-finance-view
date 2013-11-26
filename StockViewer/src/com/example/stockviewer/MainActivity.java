package com.example.stockviewer;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	private UndoBarController mUndoBarController = null;
	private StockListAdapter mStockListAdapter = null;
	private SwipeDismissListViewTouchListener mSwipeDismissListViewTouchListener = null;
	private StockListSwipeDismissCallbacks mStockListSwipeDismissCallbacks = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Set stock list and undo adapters.
        ListView stockList = (ListView) findViewById(R.id.stock_list);
        stockList.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
        mStockListAdapter = new StockListAdapter(getApplicationContext(), getDummyStocks(10));
        mUndoBarController = new UndoBarController(findViewById(R.id.undobar), mStockListAdapter);
        mStockListSwipeDismissCallbacks = new StockListSwipeDismissCallbacks(getApplicationContext(), mUndoBarController);
        mSwipeDismissListViewTouchListener = new SwipeDismissListViewTouchListener(stockList, mStockListSwipeDismissCallbacks);
        stockList.setAdapter(mStockListAdapter);
        stockList.setOnTouchListener(mSwipeDismissListViewTouchListener);
        stockList.setOnScrollListener(mSwipeDismissListViewTouchListener.makeScrollListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mUndoBarController.onSaveInstanceState(outState);
        mStockListAdapter.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mUndoBarController.onRestoreInstanceState(savedInstanceState);
        mStockListAdapter.onRestoreInstanceState(savedInstanceState);
    }
    
    private ArrayList<Stock> getDummyStocks(int n) {
    	// TODO REMOVE
    	ArrayList<Stock> list = new ArrayList<Stock>();
    	for(int i = 0; i < n; ++i) {
    		Stock stock = new Stock();
    		stock.dummy += i;
    		list.add(stock);
    	}
    	return list;
    }
}
