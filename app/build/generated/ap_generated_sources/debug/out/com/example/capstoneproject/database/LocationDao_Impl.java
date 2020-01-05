package com.example.capstoneproject.database;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.capstoneproject.objects.Location;
import java.lang.Boolean;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class LocationDao_Impl implements LocationDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Location> __insertionAdapterOfLocation;

  private final EntityDeletionOrUpdateAdapter<Location> __updateAdapterOfLocation;

  public LocationDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfLocation = new EntityInsertionAdapter<Location>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `location` (`id`,`mName`,`mAddress`,`mIsOpen`) VALUES (?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Location value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getAddress() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getAddress());
        }
        final Integer _tmp;
        _tmp = value.isOpen() == null ? null : (value.isOpen() ? 1 : 0);
        if (_tmp == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, _tmp);
        }
      }
    };
    this.__updateAdapterOfLocation = new EntityDeletionOrUpdateAdapter<Location>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR REPLACE `location` SET `id` = ?,`mName` = ?,`mAddress` = ?,`mIsOpen` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Location value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getAddress() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getAddress());
        }
        final Integer _tmp;
        _tmp = value.isOpen() == null ? null : (value.isOpen() ? 1 : 0);
        if (_tmp == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, _tmp);
        }
        if (value.getId() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getId());
        }
      }
    };
  }

  @Override
  public void insertLocation(final Location location) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfLocation.insert(location);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateLocation(final Location location) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfLocation.handle(location);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<Location>> loadLocations() {
    final String _sql = "SELECT * FROM location";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"location"}, false, new Callable<List<Location>>() {
      @Override
      public List<Location> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfMId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMName = CursorUtil.getColumnIndexOrThrow(_cursor, "mName");
          final int _cursorIndexOfMAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "mAddress");
          final int _cursorIndexOfMIsOpen = CursorUtil.getColumnIndexOrThrow(_cursor, "mIsOpen");
          final List<Location> _result = new ArrayList<Location>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Location _item;
            final String _tmpMName;
            _tmpMName = _cursor.getString(_cursorIndexOfMName);
            final String _tmpMAddress;
            _tmpMAddress = _cursor.getString(_cursorIndexOfMAddress);
            final Boolean _tmpMIsOpen;
            final Integer _tmp;
            if (_cursor.isNull(_cursorIndexOfMIsOpen)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(_cursorIndexOfMIsOpen);
            }
            _tmpMIsOpen = _tmp == null ? null : _tmp != 0;
            _item = new Location(_tmpMName,_tmpMAddress,_tmpMIsOpen);
            final String _tmpMId;
            _tmpMId = _cursor.getString(_cursorIndexOfMId);
            _item.setId(_tmpMId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Location loadLastLocationForWidget(final String id) {
    final String _sql = "SELECT * FROM location WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, id);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfMId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfMName = CursorUtil.getColumnIndexOrThrow(_cursor, "mName");
      final int _cursorIndexOfMAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "mAddress");
      final int _cursorIndexOfMIsOpen = CursorUtil.getColumnIndexOrThrow(_cursor, "mIsOpen");
      final Location _result;
      if(_cursor.moveToFirst()) {
        final String _tmpMName;
        _tmpMName = _cursor.getString(_cursorIndexOfMName);
        final String _tmpMAddress;
        _tmpMAddress = _cursor.getString(_cursorIndexOfMAddress);
        final Boolean _tmpMIsOpen;
        final Integer _tmp;
        if (_cursor.isNull(_cursorIndexOfMIsOpen)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getInt(_cursorIndexOfMIsOpen);
        }
        _tmpMIsOpen = _tmp == null ? null : _tmp != 0;
        _result = new Location(_tmpMName,_tmpMAddress,_tmpMIsOpen);
        final String _tmpMId;
        _tmpMId = _cursor.getString(_cursorIndexOfMId);
        _result.setId(_tmpMId);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
