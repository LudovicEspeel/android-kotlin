package com.example.contentprovider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

// vereenvoudigde versie t.o.v. boek: wat minder gebruik gemaakt van lamba's, maar dat geeft
// wel wat meer lijnen code...
class DatabaseProvider2 : ContentProvider() {

    private val bookDir = 0
    private val bookItem = 1
    private val categoryDir = 2
    private val categoryItem = 3
    private val authority = "com.example.contentprovider.provider"
    private var dbHelper: MyDatabaseHelper? = null

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    // body van init wordt uitgevoerd bij het aanmaken van een object van deze klasse
    init {
        uriMatcher.addURI(authority, "book", bookDir) // alle data van book
        uriMatcher.addURI(authority, "book/#", bookItem) // 1 rij data van book
        uriMatcher.addURI(authority, "category", categoryDir) // alle data van category
        uriMatcher.addURI(authority, "category/#", categoryItem) // 1 rij data van category
    }

    override fun onCreate(): Boolean {
        if (context == null)
            return false

        dbHelper = MyDatabaseHelper(context!!, "BookStore.db", 2)
        // !! is "vertellen" aan compiler dat context nooit null zal zijn (volgens developer)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        if (dbHelper == null)
            return null

        // query data
        val db = dbHelper!!.readableDatabase
        when (uriMatcher.match(uri)) {
            bookDir -> {
                return db.query("Book", projection, selection, selectionArgs, null, null, sortOrder)
            }
            bookItem -> {
                val bookId = uri.pathSegments[1]
                return db.query("Book", projection, "id = ?", arrayOf(bookId), null, null, sortOrder)
            }
            categoryDir -> {
                return db.query("Category", projection, selection, selectionArgs, null, null, sortOrder)
            }
            categoryItem -> {
                val categoryId = uri.pathSegments[1]
                return db.query("Category", projection, "id = ?", arrayOf(categoryId), null, null, sortOrder)
            }
        }
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        if (dbHelper == null)
            return null

        // add data
        val db = dbHelper!!.writableDatabase
        when (uriMatcher.match(uri)) {
            bookDir, bookItem -> {
                val newBookId = db.insert("Book", null, values)
                return Uri.parse("content://$authority/book/$newBookId")
            }
            categoryDir, categoryItem -> {
                val newCategoryId = db.insert("Category", null, values)
                return Uri.parse("content://$authority/category/$newCategoryId")
            }
        }

        return null
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        if (dbHelper == null)
            return 0

        // update data
        val db = dbHelper!!.writableDatabase
        when (uriMatcher.match(uri)) {
            bookDir -> {
                return db.update("Book", values, selection, selectionArgs)
                // geeft aantal geüpdated rijen terug
            }
            bookItem -> {
                val bookId = uri.pathSegments[1]
                return db.update("Book", values, "id = ?", arrayOf(bookId))
            }
            categoryDir -> {
                return db.update("Category", values, selection, selectionArgs)
            }
            categoryItem -> {
                val categoryId = uri.pathSegments[1]
                return db.update("Category", values, "id = ?", arrayOf(categoryId))
            }
        }

        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        if (dbHelper == null)
            return 0

        // delete data
        val db = dbHelper!!.writableDatabase
        when (uriMatcher.match(uri)) {
            bookDir -> {
                return db.delete("Book", selection, selectionArgs)
                // geeft het aantal gewiste rijen terug
            }
            bookItem -> {
                val bookId = uri.pathSegments[1]
                return db.delete("Book", "id = ?", arrayOf(bookId))
            }
            categoryDir -> {
                return db.delete("Category", selection, selectionArgs)
            }
            categoryItem -> {
                val categoryId = uri.pathSegments[1]
                return db.delete("Category", "id = ?", arrayOf(categoryId))
            }
        }

        return 0
    }

    override fun getType(uri: Uri): String? {
        when (uriMatcher.match(uri)) {
            bookDir -> {
                "vnd.android.cursor.dir/vnd.com.example.contentprovider.provider.book"
            }
            bookItem -> {
                "vnd.android.cursor.item/vnd.com.example.contentprovider.provider.book"
            }
            categoryDir -> {
                "vnd.android.cursor.dir/vnd.com.example.contentprovider.provider.category"
            }
            categoryItem -> {
                "vnd.android.cursor.item/vnd.com.example.contentprovider.provider.category"
            }
        }

        return null
    }
}