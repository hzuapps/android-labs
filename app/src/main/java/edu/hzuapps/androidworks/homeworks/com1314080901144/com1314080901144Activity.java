package com1314080901144;

import java.io.IOException;
import java.util.List;

import net.everythingandroid.smspopup.Eula;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.com1314080901144.sgf.FileList;
import com.example.com1314080901144.sgf.SgfHelper;
import com.example.com1314080901144.util.Coordinate;
import com.example.com1314080901144.util.Function;

public class Com1314080901144Activity extends Activity {
	public static final String Tag = "ChunGo";

	private TextView textView;
	private TileView tileView;

	private EditText editText;

	private Button returnBtn;
	private Button gotoBtn;
	private Button backBtn;
	private Button forwardBtn;

	private Board board;

	private String info;
	private String black;
	private String white;
	private CharSequence save;
	private CharSequence open;

	private Function infoListener = new Function() {
		@Override
		public Object apply(Object... obj) {
			int count = (Integer) obj[0];
			int expBw = (Integer) obj[1];

			updateInfo(black, white, count, expBw);

			return null;
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.com1314080901144);

		tileView = (TileView) findViewById(R.id.tileView);
		textView = (TextView) findViewById(R.id.textView);

		editText = (EditText) findViewById(R.id.editText);
		gotoBtn = (Button) findViewById(R.id.gotoBtn);

		returnBtn = (Button) findViewById(R.id.returnBtn);
		returnBtn.setEnabled(false);

		backBtn = (Button) findViewById(R.id.backBtn);
		backBtn.setEnabled(false);

		forwardBtn = (Button) findViewById(R.id.forwardBtn);
		forwardBtn.setEnabled(false);

		//  棋盘视图
		board = tileView.getBoard();
		tileView.setBackgroundColor(Color.rgb(255, 128, 64));

		// 字符串资源
		Resources r = Com1314080901144Activity.this.getResources();
		info = r.getString(R.string.info);
		black = r.getString(R.string.black);
		white = r.getString(R.string.white);
		save = r.getText(R.string.save);
		open = r.getText(R.string.open);

		// 监听事件

		board.setListener(infoListener);
		action();

		if (savedInstanceState != null) {
			Bundle map = savedInstanceState.getBundle(Tag);
			if (map != null) {
				board.restoreState(map);
			}
		}

		Log.d(Tag, "initFinished");

		Eula.show(this);
	}

	// ------------------------------------------------------------------
	private void action() {
		gotoBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				int i = 0;
				try {
					i = Integer.valueOf(String.valueOf(editText.getText()));
				} catch (Exception ex) {
					return;
				}

				Board b = board.getSubBoard(i);
				b.setListener(infoListener);
				tileView.setBoard(b);
				returnBtn.setEnabled(true);
				backBtn.setEnabled(true);
				forwardBtn.setEnabled(true);
			}
		});

		returnBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				tileView.setBoard(board);
				returnBtn.setEnabled(false);
				backBtn.setEnabled(false);
				forwardBtn.setEnabled(false);
			}
		});

		backBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (tileView.getBoard() instanceof SubBoard) {
					SubBoard sb = (SubBoard) tileView.getBoard();
					sb.back();
					tileView.invalidate();
				}
			}
		});
		forwardBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (tileView.getBoard() instanceof SubBoard) {
					SubBoard sb = (SubBoard) tileView.getBoard();
					sb.forward();
					tileView.invalidate();
				}
			}
		});
	}

	private void updateInfo(final String black, final String white, int count,
			int expBw) {
		String bw = "?";
		if (expBw == Board.Black)
			bw = black;
		else if (expBw == Board.White)
			bw = white;

		String s = String.format(info, count, bw);
		textView.setText(s);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putBundle(Tag, board.saveState());
	}

	// ------------------------------------------------------------------sgf�ļ�����

	final int menuSave = Menu.FIRST;
	final int menuOpen = Menu.FIRST + 1;
	final int menuAbout = Menu.FIRST + 2;
	final int menuClear = Menu.FIRST + 3;
	final int menuSet = Menu.FIRST + 4;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, menuOpen, 0, open).setIcon(
				android.R.drawable.ic_menu_search);
		menu.add(0, menuSave, 0, save).setIcon(android.R.drawable.ic_menu_save);
		menu.add(0, menuAbout, 0, R.string.about).setIcon(
				android.R.drawable.ic_dialog_info);
		menu.add(0, menuClear, 0, R.string.clear).setIcon(
				android.R.drawable.ic_menu_close_clear_cancel);
		menu.add(0, menuSet, 0, R.string.set).setIcon(
				android.R.drawable.ic_menu_sort_by_size);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case menuSave:
			this.showDialog(SaveDialog);
			break;
		case menuOpen:
			openSgf();
			break;
		case menuAbout:
			this.showDialog(AboutDialog);
			break;
		case menuClear:
			this.onCreate(null);
			break;
		case menuSet:
			this.showDialog(SetDialog);
			break;
		}
		return true;
	}

	private void save(String name) {
		String path = name + ".sgf";
		try {
			SgfHelper.save(board, path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void openSgf() {
		Intent i = new Intent(this, FileList.class);
		Bundle b = new Bundle();

		b.putString("filter", "sgf");
		i.putExtras(b);

		startActivityForResult(i, 10);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			Bundle b = data.getExtras();
			String string = b.getString("result");

			testSgf(string);
		}
	}

	private void testSgf(String path) {
		try {
			List<Coordinate> cs = SgfHelper.getCoordList(path);
			for (Coordinate c : cs) {
				board.put(c.x, c.y);
			}
			tileView.invalidate();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private final static int SaveDialog = 0;
	private final static int AboutDialog = 1;
	private final static int SetDialog = 2;

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case SaveDialog:
			final EditText fileNameTxt = new EditText(this);
			return new AlertDialog.Builder(this)
					.setIcon(android.R.drawable.ic_input_add)
					.setTitle("FileName").setMessage(R.string.fileName)
					.setView(fileNameTxt)
					.setPositiveButton("OK", new OnClickListener() {
						@Override
						public void onClick(DialogInterface a0, int a1) {
							save(fileNameTxt.getText().toString());
						}
					}).create();
		case AboutDialog:
			return new AlertDialog.Builder(this)
					.setIcon(android.R.drawable.ic_input_add).setTitle("About")
					.setMessage(R.string.aboutInfo)
					.setPositiveButton("OK", null).create();
		case SetDialog:
			return createSetDialog();
		default:
			return null;
		}
	}

	private Dialog createSetDialog() {
		final EditText fileNameTxt1 = new EditText(this);
		return new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_menu_sort_by_size)
				.setTitle("set").setMessage(R.string.boardSize)
				.setView(fileNameTxt1)
				.setPositiveButton("OK", new OnClickListener() {
					@Override
					public void onClick(DialogInterface a0, int a1) {
						int i = 0;
						try {
							i = Integer.valueOf(String.valueOf(fileNameTxt1
									.getText()));
						} catch (Exception ex) {
							return;
						}
						if (i < 2)
							return;
						Board.n = i;
						Com1314080901144Activity.this.onCreate(null);
					}
				}).create();
	}

}