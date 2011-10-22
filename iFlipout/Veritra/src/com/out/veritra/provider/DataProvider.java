package com.out.veritra.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;


public class DataProvider extends ContentProvider {

  private static final int Document = 1;
  private static final int Document_ID = 2;
  private static final int DocumentImageCache = 3;
  private static final int DocumentImageCache_ID = 4;
  private static final int DocumentCache = 5;
  private static final int DocumentCache_ID = 6;
  private static final int DocumentListCache = 7;
  private static final int DocumentListCache_ID = 8;
  private static final int CategoriesCache = 9;
  private static final int CategoriesCache_ID = 10;

  private static final String uri = "com.scribd.scribdroid";


  private SQLiteOpenHelper dbManager;

  //A UriMatcher class to aid in matching URIs in content providers. 
  private static final UriMatcher root = new UriMatcher(UriMatcher.NO_MATCH);

  static {
    root.addURI(uri, "Document", Document);
    root.addURI(uri, "Document/#", Document_ID);
    root.addURI(uri, "DocumentImageCache", DocumentImageCache);
    root.addURI(uri, "DocumentImageCache/#", DocumentImageCache_ID);
    root.addURI(uri, "DocumentCache", DocumentCache);
    root.addURI(uri, "DocumentCache/#", DocumentCache_ID);
    root.addURI(uri, "DocumentListCache", DocumentListCache);
    root.addURI(uri, "DocumentListCache/#", DocumentListCache_ID);
    root.addURI(uri, "CategoriesCache", CategoriesCache);
    root.addURI(uri, "CategoriesCache/#", CategoriesCache_ID);

  }

  public static final String DEFAULT_SORT_ORDER = "_id ASC";

  public static class DocumentColumns implements BaseColumns {
    public static final Uri URI = Uri.parse("content://" + uri + "/Document");

    public static final String ID = "documentID";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String THUMBNAIL_URL = "thumbnailUrl";
    public static final String AUTHOR = "author";
    public static final String PAGE_COUNT = "pagecount";

    public static final int ID_INDEX = 0;
    public static final int TITLE_INDEX = 1;
    public static final int DESCRIPTION_INDEX = 2;
    public static final int THUMBNAIL_URL_INDEX = 3;
    public static final int AUTHOR_INDEX = 4;
    public static final int PAGE_COUNT_INDEX = 5;

    public static final String[] DOCUMENT_COLUMNS = {
      ID, TITLE, DESCRIPTION, THUMBNAIL_URL, AUTHOR, PAGE_COUNT
    };
  }

  public static class DocumentCacheColumns implements BaseColumns {
    public static final Uri URI = Uri.parse("content://" + uri + "/DocumentCache");

    public static final String DOCUMENT_ID = "documentID";
    public static final String FILENAME = "filename";

    public static final int DOCUMENT_ID_INDEX = 0;
    public static final int FILENAME_INDEX = 1;

    public static final String[] COLUMNS = { DOCUMENT_ID, FILENAME };
  }

  public static class DocumentImageCacheColumns implements BaseColumns {
    public static final Uri URI = Uri.parse("content://" + uri + "/DocumentImageCache");
    public static final String DOCUMENT_ID = "documentID";
    public static final String FILENAME = "filename";

    public static final int DOCUMENT_ID_INDEX = 0;
    public static final int FILENAME_INDEX = 1;

    public static final String[] COLUMNS = { DOCUMENT_ID, FILENAME };
  }

  public static class DocumentListCacheColumns implements BaseColumns {
    public static final Uri URI = Uri.parse("content://" + uri + "/DocumentListCache");

    public static final String LIST_ID = "listID";
    public static final String DOCUMENT_ID = "documentID";

    public static final int LIST_ID_INDEX = 0;
    public static final int DOCUMENT_ID_INDEX = 1;

    public static final String[] COLUMNS = { DOCUMENT_ID, LIST_ID };
  }

  public static class CategoriesCacheColumns implements BaseColumns {
    public static final Uri URI = Uri.parse("content://" + uri + "/CategoriesCache");

    public static final String CATEGORY_ID = "categoryID";
    public static final String TITLE = "title";
    public static final String PARENT_ID = "parentID";

    public static final int CATEGORY_ID_INDEX = 0;
    public static final int TITLE_INDEX = 1;
    public static final int PARENT_INDEX = 2;

    public static final String[] COLUMNS = { CATEGORY_ID, TITLE, PARENT_ID };

  }

  private static class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "scribd.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
      super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

      db.execSQL("CREATE TABLE Document (" +
          "documentID TEXT PRIMARY KEY, title TEXT, description TEXT, thumbnailUrl TEXT, author TEXT," +
      "pagecount INTEGER);");

      db.execSQL("CREATE TABLE DocumentImageCache (documentID TEXT PRIMARY KEY, filename TEXT);");
      db.execSQL("CREATE TABLE DocumentCache (documentID TEXT PRIMARY KEY, filename TEXT);");

      db.execSQL("CREATE TABLE DocumentListCache (listID TEXT, documentID TEXT");

      db.execSQL("CREATE TABLE CategoriesCache(categoryID TEXT PRIMARY KEY, title TEXT, parentID TEXT);");

      String insert = "INSERT INTO Document(documentID, title, description, thumbnailUrl, author, pagecount) VALUES (";
      db.execSQL(insert + "'0022','Test','Testing','url','Test Author', '1');");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
        int currentVersion) {
      db.execSQL("DROP TABLE IF EXISTS Document");
      db.execSQL("DROP TABLE IF EXISTS DocumentImageCache");
      db.execSQL("DROP TABLE IF EXISTS DocumentCache");
      db.execSQL("DROP TABLE IF EXISTS DocumentListCache");
      db.execSQL("DROP TABLE IF EXISTS CategoriesCache");

      onCreate(db);
    }
  }

  // A request to delete one or more rows from the Table

  @Override
  public int delete(Uri uri, String selection, String[] selectionArgs) {
    SQLiteDatabase db = dbManager.getWritableDatabase();


    int count = 0;

    if (uri.getPathSegments() != null && uri.getPathSegments().size() > 1) {
      String segment = uri.getPathSegments().get(1);
      if (TextUtils.isEmpty(selection)) {
        selection = "_id=" + segment;
      } else {
        selection = "_id=" + segment + " AND (" + selection + ")";
      }
    }

    switch (root.match(uri)) {
    case Document:
    case Document_ID:
      count = db.delete("Document", selection, selectionArgs);
      break;
    case DocumentImageCache:
    case DocumentImageCache_ID:
      count = db.delete("DocumentImageCache", selection, selectionArgs);
      break;
    case DocumentCache:
    case DocumentCache_ID:
      count = db.delete("DocumentCache", selection, selectionArgs);
      break;
    case DocumentListCache:
    case DocumentListCache_ID:
      count = db.delete("DocumentListCache", selection, selectionArgs);
      break;
    case CategoriesCache:
    case CategoriesCache_ID:
      count = db.delete("CategoriesCache", selection, selectionArgs);
      break;
    default:
      throw new IllegalArgumentException("Cannot delete from URL: " + uri);
    }

    getContext().getContentResolver().notifyChange(uri, null);
    db.close();
    return count;
  }

  // Return the MIME type of the data at the given URI.

  @Override
  public String getType(Uri uri) {
    int match = root.match(uri);//Try to match against the path in a url.
    switch (match) {
    case Document:
      return "vnd.android.cursor.dir/Document";
    case Document_ID:
      return "vnd.android.cursor.item/Document";
    case DocumentImageCache:
      return "vnd.android.cursor.dir/DocumentImageCache";
    case DocumentImageCache_ID:
      return "vnd.android.cursor.item/DocumentImageCache";
    case DocumentCache:
      return "vnd.android.cursor.dir/DocumentCache";
    case DocumentCache_ID:
      return "vnd.android.cursor.item/DocumentCache";
    case DocumentListCache:
      return "vnd.android.cursor.dir/DocumentListCache";
    case DocumentListCache_ID:
      return "vnd.android.cursor.item/DocumentListCache";
    case CategoriesCache:
      return "vnd.android.cursor.dir/CategoriesCache";
    case CategoriesCache_ID:
      return "vnd.android.cursor.item/CategoriesCache";
    default:
      throw new IllegalArgumentException("Unknown URL");
    }
  }

  // Implement this to insert a new row in a Table

  @Override
  public Uri insert(Uri uri, ContentValues contentValues) {
    int match = root.match(uri);
    String table;
    switch (match) {
    case Document:
      table = "Document";
      break;
    case DocumentImageCache:
      table = "DocumentImageCache";
      break;
    case DocumentCache:
      table = "DocumentCache";
      break;
    case DocumentListCache:
      table = "DocumentListCache";
      break;
    case CategoriesCache:
      table = "CategoriesCache";
      break;
    default:
      throw new IllegalArgumentException("Unknown URL " + uri);
    }
    ContentValues values;

    if (contentValues != null) {
      values = new ContentValues(contentValues);
    } else {
      values = new ContentValues();
    }


    SQLiteDatabase db = dbManager.getWritableDatabase();
    long rowId = db.insert(table, "", values);


    if (rowId < 0) {
      throw new SQLException("Failed to insert row into " + uri);
    }

    Uri currentUri = null;

    if (table.equalsIgnoreCase("Document")) {
      currentUri = DocumentColumns.URI;
    } 

    if (table.equalsIgnoreCase("DocumentImageCache")) {
      currentUri = DocumentImageCacheColumns.URI;
    }

    if (table.equalsIgnoreCase("DocumentListCache")) {
      currentUri = DocumentListCacheColumns.URI;
    }

    if (table.equalsIgnoreCase("DocumentCache")) {
      currentUri = DocumentCacheColumns.URI;
    }

    if (table.equalsIgnoreCase("CategoriesCache")) {
      currentUri = CategoriesCacheColumns.URI;
    }

    Uri newUri = ContentUris.withAppendedId(currentUri, rowId);

    getContext().getContentResolver().notifyChange(newUri, null);
    return newUri;
  }

  @Override
  public boolean onCreate() {
    dbManager = new DatabaseHelper(getContext());
    return false;
  }

  // Receives a query request from a client in a local process, and returns a
  // Cursor.

  @Override
  public Cursor query(Uri uri, String[] projection, String selection,
      String[] selectionArgs, String sortOrder) {
    // This is a class that build SQL queries to be sent to SQLiteDatabase
    // objects.

    SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

    // Generate the body of the query
    int match = root.match(uri);
    switch (match) {
    case Document:
    case Document_ID:
      qb.setTables("Document");// Sets the list of tables to query
      break;

    case DocumentImageCache:
    case DocumentImageCache_ID:
      qb.setTables("DocumentImageCache");// Sets the list of tables to query
      break;

    case DocumentCache:
    case DocumentCache_ID:
      qb.setTables("DocumentCache");// Sets the list of tables to query
      break;

    case DocumentListCache:
    case DocumentListCache_ID:
      qb.setTables("DocumentListCache");// Sets the list of tables to query
      break;

    case CategoriesCache:
    case CategoriesCache_ID:
      qb.setTables("CategoriesCache");// Sets the list of tables to query
      break;

    default:
      throw new IllegalArgumentException("Unknown URL " + uri);
    }

    if (uri.getPathSegments() != null && uri.getPathSegments().size() > 1) {
      qb.appendWhere("_id=");// Append a chunk to the WHERE clause of the
      qb.appendWhere(uri.getPathSegments().get(1));
    }

    // Exposes methods to manage a SQLite database.

    SQLiteDatabase db = dbManager.getReadableDatabase();
    Cursor cursor = qb.query(db, projection, selection, selectionArgs,
        null, null, sortOrder);

    if (cursor != null) {
      cursor.setNotificationUri(getContext().getContentResolver(), uri);
    }

    return cursor;
  }

  // Update a content URI.

  @Override
  public int update(Uri uri, ContentValues values, String selection,
      String[] selectionArgs) {
    SQLiteDatabase db = dbManager.getWritableDatabase();
    int match = root.match(uri);
    String table = null;
    String whereClause = null;
    String column = "_id=";

    switch (match) {
    case Document:
    case Document_ID:
      table = "Document";// Sets the list of tables to query
      break;

    case DocumentImageCache:
    case DocumentImageCache_ID:
      table = "DocumentImageCache";// Sets the list of tables to query
      break;

    case DocumentCache:
    case DocumentCache_ID:
      table = "DocumentCache";// Sets the list of tables to query
      break;

    case DocumentListCache:
    case DocumentListCache_ID:
      table = "DocumentListCache";// Sets the list of tables to query
      break;

    case CategoriesCache:
    case CategoriesCache_ID:
      table = "CategoriesCache";// Sets the list of tables to query
      break;

    default:
      throw new IllegalArgumentException("Unknown URL " + uri);

    }

    if (uri.getPathSegments() != null && uri.getPathSegments().size() > 1) {
      long rowId = Long.parseLong(uri.getPathSegments().get(1));
      whereClause = column + rowId;
    }

    int count = db.update(table, values, whereClause, null);
    getContext().getContentResolver().notifyChange(uri, null);
    return count;
  }

}

