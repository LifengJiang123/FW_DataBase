package com.unisoc.fw_database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * 数据的提供类
 * 专门进行数据库的增删改查
 */

public class MyContentProvider extends ContentProvider {

    // 进行数据库的增删改查是数据库对象完成的，声明数据库对象
    private MyDataBaseHelper myDataBaseHelper;
    private SQLiteDatabase db;

    private static final String AUTHORITY = "com.unisoc.fw_database.provider";

    private static final int bookDir = 0;
    private static final int bookItem = 1;

    // content://<authority>/<path>/<id>
    // 声明UriMatcher对象
    private static final UriMatcher uriMatcher;

    static {

        // 实例化UriMatcher对象
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        // 实现匹配URI的功能 参数：1 authority 2 path 3 id
        uriMatcher.addURI(AUTHORITY, "Book", bookDir);
        // content://<authority>/<path>/#  匹配表中的任意一行数据
        uriMatcher.addURI(AUTHORITY, "Book/#", bookItem);
    }

    public MyContentProvider() {
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        // 创建MyDataBaseHelper实例对象
        myDataBaseHelper = new MyDataBaseHelper(getContext());
        // 写的方式打开数据库，返回数据库对象
        //modify1--------换位置，保证db不为null
        db = myDataBaseHelper.getWritableDatabase();
//        myDataBaseHelper = new MyDataBaseHelper(getContext(), "MyDatabase.db", null ,2);
        return true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        switch (uriMatcher.match(uri)){   // 返回自定义的id码
            case bookDir:
                int a = db.delete("Book", selection, selectionArgs);
                return a;
            case bookItem:
                // getPathSegments以列表的形式返回uri的路径片段，从authority开始的
                String bookId = uri.getPathSegments().get(1);  // id
                int b = db.delete("Book","id = ?", new String[]{bookId});
                return b;
            default:
        }
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        // 创建数据库对象
        // 解析内容Uri
        switch (uriMatcher.match(uri)){
            case bookDir:
            case bookItem:
                long newBookId = db.insert("Book", null, values);
                Uri newuri = Uri.parse("content://" + AUTHORITY + "/Book/" + String.valueOf(newBookId));
                return newuri;
            default:
                break;
        }
        return null;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        //modify2---cursor没赋值，一直为null
        Cursor cursor = null;
        switch (uriMatcher.match(uri)){
            case bookDir:
                cursor = db.query("Book", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case bookItem:
                String queryId = uri.getPathSegments().get(1);
                cursor = db.query("Book", projection, "id = ?", new String[]{queryId}, null, null, sortOrder);
            default:

        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        switch (uriMatcher.match(uri)){
            case bookDir:
                int a = db.update("Book", values, selection, selectionArgs);
                return a;
            case bookItem:
                String updateId = uri.getPathSegments().get(1);
                int b = db.update("Book", values, "id = ?", new String[]{updateId});
                return b;
            default:
        }
        return 0;
    }
}