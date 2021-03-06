/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 1.3.40
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.sleepycat.db.internal;

import com.sleepycat.db.*;
import java.util.Comparator;

public class DbEnv {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected DbEnv(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(DbEnv obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  /* package */ synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        throw new UnsupportedOperationException("C++ destructor does not have public access");
      }
      swigCPtr = 0;
    }
  }

	/*
	 * Internally, the JNI layer creates a global reference to each DbEnv,
	 * which can potentially be different to this.  We keep a copy here so
	 * we can clean up after destructors.
	 */
	private long dbenv_ref;
	public Environment wrapper;

	private LogRecordHandler app_dispatch_handler;
	private EventHandler event_notify_handler;
	private FeedbackHandler env_feedback_handler;
	private ErrorHandler error_handler;
	private String errpfx;
	private MessageHandler message_handler;
	private PanicHandler panic_handler;
	private ReplicationTransport rep_transport_handler;
	private java.io.OutputStream error_stream;
	private java.io.OutputStream message_stream;
	private ThreadLocal errBuf;

	public static class RepProcessMessage {
		public int envid;
	}

	/*
	 * Called by the public DbEnv constructor and for private environments
	 * by the Db constructor.
	 */
	void initialize() {
		dbenv_ref = db_java.initDbEnvRef0(this, this);
		errBuf = new ThreadLocal();
		/* Start with System.err as the default error stream. */
		set_error_stream(System.err);
		set_message_stream(System.out);
	}

	void cleanup() {
		swigCPtr = 0;
		db_java.deleteRef0(dbenv_ref);
		dbenv_ref = 0L;
	}

	public synchronized void close(int flags) throws DatabaseException {
		try {
			close0(flags);
		} finally {
			cleanup();
		}
	}

	private final int handle_app_dispatch(DatabaseEntry dbt,
					      LogSequenceNumber lsn,
					      int recops) {
		return app_dispatch_handler.handleLogRecord(wrapper, dbt, lsn,
		    RecoveryOperation.fromFlag(recops));
	}

	public LogRecordHandler get_app_dispatch() throws com.sleepycat.db.DatabaseException {
		return app_dispatch_handler;
	}

	private final void handle_panic_event_notify() {
		event_notify_handler.handlePanicEvent();
	}

	private final void handle_rep_client_event_notify() {
		event_notify_handler.handleRepClientEvent();
	}

	private final void handle_rep_elected_event_notify() {
		event_notify_handler.handleRepElectedEvent();
	}

	private final void handle_rep_master_event_notify() {
		event_notify_handler.handleRepMasterEvent();
	}

	private final void handle_rep_new_master_event_notify(int envid) {
		event_notify_handler.handleRepNewMasterEvent(envid);
	}

	private final void handle_rep_perm_failed_event_notify() {
		event_notify_handler.handleRepPermFailedEvent();
	}

	private final void handle_rep_startup_done_event_notify() {
		event_notify_handler.handleRepStartupDoneEvent();
	}

	private final void handle_write_failed_event_notify(int errno) {
		event_notify_handler.handleWriteFailedEvent(errno);
	}

	public EventHandler get_event_notify() throws com.sleepycat.db.DatabaseException {
		return event_notify_handler;
	}

	private final void handle_env_feedback(int opcode, int percent) {
		if (opcode == DbConstants.DB_RECOVER)
			env_feedback_handler.recoveryFeedback(wrapper, percent);
		/* No other environment feedback type supported. */
	}

	public FeedbackHandler get_feedback() throws com.sleepycat.db.DatabaseException {
		return env_feedback_handler;
	}

	public void set_errpfx(String errpfx) /* no exception */ {
		this.errpfx = errpfx;
	}

	public String get_errpfx() /* no exception */ {
		return errpfx;
	}

	private final void handle_error(String msg) {
		com.sleepycat.util.ErrorBuffer ebuf = (com.sleepycat.util.ErrorBuffer)errBuf.get();
		if (ebuf == null) {
			/*
			 * Populate the errBuf ThreadLocal on demand, since the
			 * callback can be made from different threads.
			 */
			ebuf = new com.sleepycat.util.ErrorBuffer(3);
			errBuf.set(ebuf);
		}
		ebuf.append(msg);
		error_handler.error(wrapper, this.errpfx, msg);
	}

	private final String get_err_msg(String orig_msg) {
		com.sleepycat.util.ErrorBuffer ebuf = (com.sleepycat.util.ErrorBuffer)errBuf.get();
		String ret = null;
		if (ebuf != null) {
			ret = ebuf.get();
			ebuf.clear();
		}
		if (ret != null && ret.length() > 0)
			return orig_msg + ": " + ret;
		return orig_msg;
	}

	public ErrorHandler get_errcall() /* no exception */ {
		return error_handler;
	}

	private final void handle_message(String msg) {
		message_handler.message(wrapper, msg);
	}

	public MessageHandler get_msgcall() /* no exception */ {
		return message_handler;
	}

	private final void handle_panic(DatabaseException e) {
		panic_handler.panic(wrapper, e);
	}

	public PanicHandler get_paniccall() throws com.sleepycat.db.DatabaseException {
		return panic_handler;
	}

	private final int handle_rep_transport(DatabaseEntry control,
					       DatabaseEntry rec,
					       LogSequenceNumber lsn,
					       int envid, int flags)
	    throws DatabaseException {
		return rep_transport_handler.send(wrapper,
		    control, rec, lsn, envid,
		    (flags & DbConstants.DB_REP_NOBUFFER) != 0,
		    (flags & DbConstants.DB_REP_PERMANENT) != 0,
		    (flags & DbConstants.DB_REP_ANYWHERE) != 0,
		    (flags & DbConstants.DB_REP_REREQUEST) != 0);
	}

	public void lock_vec(/*u_int32_t*/ int locker, int flags,
			     LockRequest[] list, int offset, int count)
	    throws DatabaseException {
		db_javaJNI.DbEnv_lock_vec(swigCPtr, this, locker, flags, list,
		    offset, count);
	}

	public synchronized void remove(String db_home, int flags)
	    throws DatabaseException, java.io.FileNotFoundException {
		try {
			remove0(db_home, flags);
		} finally {
			cleanup();
		}
	}

	public void set_error_stream(java.io.OutputStream stream) /* no exception */ {
		error_stream = stream;
		final java.io.PrintWriter pw = new java.io.PrintWriter(stream);
		set_errcall(new ErrorHandler() {
			public void error(Environment env,
			    String prefix, String buf) /* no exception */ {
				if (prefix != null)
					pw.print(prefix + ": ");
				pw.println(buf);
				pw.flush();
			}
		});
	}

	public java.io.OutputStream get_error_stream() /* no exception */ {
		return error_stream;
	}

	public void set_message_stream(java.io.OutputStream stream) /* no exception */ {
		message_stream = stream;
		final java.io.PrintWriter pw = new java.io.PrintWriter(stream);
		set_msgcall(new MessageHandler() {
			public void message(Environment env, String msg) /* no exception */
			    /* no exception */ {
				pw.println(msg);
				pw.flush();
			}
		});
	}

	public java.io.OutputStream get_message_stream() /* no exception */ {
		return message_stream;
	}

	public void set_tx_timestamp(java.util.Date timestamp) throws com.sleepycat.db.DatabaseException {
		set_tx_timestamp0(timestamp.getTime()/1000);
	}

  public DbEnv(int flags) throws com.sleepycat.db.DatabaseException {
    this(db_javaJNI.new_DbEnv(flags), true);
    initialize();
  }

  /* package */ void close0(int flags) { db_javaJNI.DbEnv_close0(swigCPtr, this, flags); }

  public void dbremove(DbTxn txnid, String file, String database, int flags) throws com.sleepycat.db.DatabaseException, java.io.FileNotFoundException { db_javaJNI.DbEnv_dbremove(swigCPtr, this, DbTxn.getCPtr(txnid), txnid, file, database, flags); }

  public void dbrename(DbTxn txnid, String file, String database, String newname, int flags) throws com.sleepycat.db.DatabaseException, java.io.FileNotFoundException { db_javaJNI.DbEnv_dbrename(swigCPtr, this, DbTxn.getCPtr(txnid), txnid, file, database, newname, flags); }

  public void err(int error, String message) /* no exception */ {
    db_javaJNI.DbEnv_err(swigCPtr, this, error, message);
  }

  public void errx(String message) /* no exception */ {
    db_javaJNI.DbEnv_errx(swigCPtr, this, message);
  }

  public DbTxn cdsgroup_begin() throws com.sleepycat.db.DatabaseException {
    long cPtr = db_javaJNI.DbEnv_cdsgroup_begin(swigCPtr, this);
    return (cPtr == 0) ? null : new DbTxn(cPtr, false);
  }

  public void fileid_reset(String file, int flags) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_fileid_reset(swigCPtr, this, file, flags); }

  public String[] get_data_dirs() throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_get_data_dirs(swigCPtr, this); }

  public int get_encrypt_flags() throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_get_encrypt_flags(swigCPtr, this); }

  public int get_flags() throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_get_flags(swigCPtr, this); }

  public String get_home() throws com.sleepycat.db.DatabaseException {
    return db_javaJNI.DbEnv_get_home(swigCPtr, this);
  }

  public String get_intermediate_dir_mode() throws com.sleepycat.db.DatabaseException {
    return db_javaJNI.DbEnv_get_intermediate_dir_mode(swigCPtr, this);
  }

  public int get_open_flags() throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_get_open_flags(swigCPtr, this); }

  public long get_shm_key() throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_get_shm_key(swigCPtr, this); }

  public String get_tmp_dir() throws com.sleepycat.db.DatabaseException {
    return db_javaJNI.DbEnv_get_tmp_dir(swigCPtr, this);
  }

  public boolean get_verbose(int which) throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_get_verbose(swigCPtr, this, which); }

  public boolean is_bigendian() throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_is_bigendian(swigCPtr, this); }

  public void lsn_reset(String file, int flags) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_lsn_reset(swigCPtr, this, file, flags); }

  public void open(String db_home, int flags, int mode) throws com.sleepycat.db.DatabaseException, java.io.FileNotFoundException { db_javaJNI.DbEnv_open(swigCPtr, this, db_home, flags, mode); }

  /* package */ void remove0(String db_home, int flags) { db_javaJNI.DbEnv_remove0(swigCPtr, this, db_home, flags); }

  public void set_cachesize(long bytes, int ncache) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_set_cachesize(swigCPtr, this, bytes, ncache); }

  public void set_cache_max(long bytes) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_set_cache_max(swigCPtr, this, bytes); }

  public void set_create_dir(String dir) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_set_create_dir(swigCPtr, this, dir); }

  public void set_data_dir(String dir) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_set_data_dir(swigCPtr, this, dir); }

  public void set_intermediate_dir_mode(String mode) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_set_intermediate_dir_mode(swigCPtr, this, mode); }

  public void set_encrypt(String passwd, int flags) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_set_encrypt(swigCPtr, this, passwd, flags); }

  public void set_errcall(com.sleepycat.db.ErrorHandler db_errcall_fcn) /* no exception */ {
    db_javaJNI.DbEnv_set_errcall(swigCPtr, this,  (error_handler = db_errcall_fcn) != null );
  }

  public void set_flags(int flags, boolean onoff) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_set_flags(swigCPtr, this, flags, onoff); }

  public void set_feedback(com.sleepycat.db.FeedbackHandler env_feedback_fcn) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_set_feedback(swigCPtr, this,  (env_feedback_handler = env_feedback_fcn) != null ); }

  public void set_mp_max_openfd(int maxopenfd) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_set_mp_max_openfd(swigCPtr, this, maxopenfd); }

  public void set_mp_max_write(int maxwrite, long maxwrite_sleep) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_set_mp_max_write(swigCPtr, this, maxwrite, maxwrite_sleep); }

  public void set_mp_mmapsize(long mp_mmapsize) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_set_mp_mmapsize(swigCPtr, this, mp_mmapsize); }

  public void set_mp_pagesize(long mp_pagesize) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_set_mp_pagesize(swigCPtr, this, mp_pagesize); }

  public void set_mp_tablesize(long mp_tablesize) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_set_mp_tablesize(swigCPtr, this, mp_tablesize); }

  public void set_msgcall(com.sleepycat.db.MessageHandler db_msgcall_fcn) /* no exception */ {
    db_javaJNI.DbEnv_set_msgcall(swigCPtr, this,  (message_handler = db_msgcall_fcn) != null );
  }

  public void set_paniccall(com.sleepycat.db.PanicHandler db_panic_fcn) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_set_paniccall(swigCPtr, this,  (panic_handler = db_panic_fcn) != null ); }

  public void set_rpc_server(String host, long cl_timeout, long sv_timeout, int flags) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_set_rpc_server(swigCPtr, this, host, cl_timeout, sv_timeout, flags); }

  public void set_shm_key(long shm_key) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_set_shm_key(swigCPtr, this, shm_key); }

  public void set_timeout(long timeout, int flags) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_set_timeout(swigCPtr, this, timeout, flags); }

  public void set_tmp_dir(String dir) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_set_tmp_dir(swigCPtr, this, dir); }

  public void set_tx_max(int max) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_set_tx_max(swigCPtr, this, max); }

  public void set_app_dispatch(com.sleepycat.db.LogRecordHandler tx_recover) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_set_app_dispatch(swigCPtr, this,  (app_dispatch_handler = tx_recover) != null ); }

  public void set_event_notify(com.sleepycat.db.EventHandler event_notify) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_set_event_notify(swigCPtr, this,  (event_notify_handler = event_notify) != null ); }

  /* package */ void set_tx_timestamp0(long timestamp) { db_javaJNI.DbEnv_set_tx_timestamp0(swigCPtr, this, timestamp); }

  public void set_verbose(int which, boolean onoff) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_set_verbose(swigCPtr, this, which, onoff); }

  public byte[][] get_lk_conflicts() throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_get_lk_conflicts(swigCPtr, this); }

  public int get_lk_detect() throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_get_lk_detect(swigCPtr, this); }

  public int get_lk_max_locks() throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_get_lk_max_locks(swigCPtr, this); }

  public int get_lk_max_lockers() throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_get_lk_max_lockers(swigCPtr, this); }

  public int get_lk_max_objects() throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_get_lk_max_objects(swigCPtr, this); }

  public int get_lk_partitions() throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_get_lk_partitions(swigCPtr, this); }

  public int lock_detect(int flags, int atype) throws com.sleepycat.db.DatabaseException {
    return db_javaJNI.DbEnv_lock_detect(swigCPtr, this, flags, atype);
  }

  public DbLock lock_get(int locker, int flags, com.sleepycat.db.DatabaseEntry object, int lock_mode) throws com.sleepycat.db.DatabaseException {
    long cPtr = db_javaJNI.DbEnv_lock_get(swigCPtr, this, locker, flags, object, lock_mode);
    return (cPtr == 0) ? null : new DbLock(cPtr, true);
  }

  public int lock_id() throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_lock_id(swigCPtr, this); }

  public void lock_id_free(int id) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_lock_id_free(swigCPtr, this, id); }

  public void lock_put(DbLock lock) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_lock_put(swigCPtr, this, DbLock.getCPtr(lock), lock); }

  public com.sleepycat.db.LockStats lock_stat(int flags) throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_lock_stat(swigCPtr, this, flags); }

  public void set_lk_conflicts(byte[][] conflicts) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_set_lk_conflicts(swigCPtr, this, conflicts); }

  public void set_lk_detect(int detect) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_set_lk_detect(swigCPtr, this, detect); }

  public void set_lk_max_lockers(int max) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_set_lk_max_lockers(swigCPtr, this, max); }

  public void set_lk_max_locks(int max) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_set_lk_max_locks(swigCPtr, this, max); }

  public void set_lk_max_objects(int max) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_set_lk_max_objects(swigCPtr, this, max); }

  public void set_lk_partitions(int partitions) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_set_lk_partitions(swigCPtr, this, partitions); }

  public int get_lg_bsize() throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_get_lg_bsize(swigCPtr, this); }

  public String get_lg_dir() throws com.sleepycat.db.DatabaseException {
    return db_javaJNI.DbEnv_get_lg_dir(swigCPtr, this);
  }

  public int get_lg_filemode() throws com.sleepycat.db.DatabaseException {
    return db_javaJNI.DbEnv_get_lg_filemode(swigCPtr, this);
  }

  public int get_lg_max() throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_get_lg_max(swigCPtr, this); }

  public int get_lg_regionmax() throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_get_lg_regionmax(swigCPtr, this); }

  public String[] log_archive(int flags) throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_log_archive(swigCPtr, this, flags); }

  public static int log_compare(com.sleepycat.db.LogSequenceNumber lsn0, com.sleepycat.db.LogSequenceNumber lsn1) /* no exception */ {
    return db_javaJNI.DbEnv_log_compare(lsn0, lsn1);
  }

  public DbLogc log_cursor(int flags) throws com.sleepycat.db.DatabaseException {
    long cPtr = db_javaJNI.DbEnv_log_cursor(swigCPtr, this, flags);
    return (cPtr == 0) ? null : new DbLogc(cPtr, true);
  }

  public String log_file(com.sleepycat.db.LogSequenceNumber lsn) throws com.sleepycat.db.DatabaseException {
    return db_javaJNI.DbEnv_log_file(swigCPtr, this, lsn);
  }

  public void log_flush(com.sleepycat.db.LogSequenceNumber lsn_or_null) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_log_flush(swigCPtr, this, lsn_or_null); }

  public boolean log_get_config(int which) throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_log_get_config(swigCPtr, this, which); }

  public void log_put(com.sleepycat.db.LogSequenceNumber lsn, com.sleepycat.db.DatabaseEntry data, int flags) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_log_put(swigCPtr, this, lsn, data, flags); }

  public void log_print(DbTxn txn, String msg) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_log_print(swigCPtr, this, DbTxn.getCPtr(txn), txn, msg); }

  public void log_set_config(int which, boolean onoff) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_log_set_config(swigCPtr, this, which, onoff); }

  public com.sleepycat.db.LogStats log_stat(int flags) throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_log_stat(swigCPtr, this, flags); }

  public void set_lg_bsize(int lg_bsize) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_set_lg_bsize(swigCPtr, this, lg_bsize); }

  public void set_lg_dir(String dir) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_set_lg_dir(swigCPtr, this, dir); }

  public void set_lg_filemode(int mode) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_set_lg_filemode(swigCPtr, this, mode); }

  public void set_lg_max(int lg_max) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_set_lg_max(swigCPtr, this, lg_max); }

  public void set_lg_regionmax(int lg_regionmax) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_set_lg_regionmax(swigCPtr, this, lg_regionmax); }

  public long get_cachesize() throws com.sleepycat.db.DatabaseException {
    return db_javaJNI.DbEnv_get_cachesize(swigCPtr, this);
  }

  public int get_cachesize_ncache() throws com.sleepycat.db.DatabaseException {
    return db_javaJNI.DbEnv_get_cachesize_ncache(swigCPtr, this);
  }

  public long get_cache_max() throws com.sleepycat.db.DatabaseException {
    return db_javaJNI.DbEnv_get_cache_max(swigCPtr, this);
  }

  public String get_create_dir() throws com.sleepycat.db.DatabaseException {
    return db_javaJNI.DbEnv_get_create_dir(swigCPtr, this);
  }

  public int get_mp_max_openfd() throws com.sleepycat.db.DatabaseException {
    return db_javaJNI.DbEnv_get_mp_max_openfd(swigCPtr, this);
  }

  public int get_mp_max_write() throws com.sleepycat.db.DatabaseException {
    return db_javaJNI.DbEnv_get_mp_max_write(swigCPtr, this);
  }

  public long get_mp_max_write_sleep() throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_get_mp_max_write_sleep(swigCPtr, this); }

  public long get_mp_mmapsize() throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_get_mp_mmapsize(swigCPtr, this); }

  public int get_mp_pagesize() throws com.sleepycat.db.DatabaseException {
    return db_javaJNI.DbEnv_get_mp_pagesize(swigCPtr, this);
  }

  public int get_mp_tablesize() throws com.sleepycat.db.DatabaseException {
    return db_javaJNI.DbEnv_get_mp_tablesize(swigCPtr, this);
  }

  public com.sleepycat.db.CacheStats memp_stat(int flags) throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_memp_stat(swigCPtr, this, flags); }

  public com.sleepycat.db.CacheFileStats[] memp_fstat(int flags) throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_memp_fstat(swigCPtr, this, flags); }

  public void memp_sync(com.sleepycat.db.LogSequenceNumber lsn) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_memp_sync(swigCPtr, this, lsn); }

  public int memp_trickle(int percent) throws com.sleepycat.db.DatabaseException {
    return db_javaJNI.DbEnv_memp_trickle(swigCPtr, this, percent);
  }

  public int mutex_get_align() throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_mutex_get_align(swigCPtr, this); }

  public int mutex_get_increment() throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_mutex_get_increment(swigCPtr, this); }

  public int mutex_get_max() throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_mutex_get_max(swigCPtr, this); }

  public int mutex_get_tas_spins() throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_mutex_get_tas_spins(swigCPtr, this); }

  public void mutex_set_align(int align) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_mutex_set_align(swigCPtr, this, align); }

  public void mutex_set_increment(int increment) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_mutex_set_increment(swigCPtr, this, increment); }

  public void mutex_set_max(int mutex_max) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_mutex_set_max(swigCPtr, this, mutex_max); }

  public void mutex_set_tas_spins(int tas_spins) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_mutex_set_tas_spins(swigCPtr, this, tas_spins); }

  public com.sleepycat.db.MutexStats mutex_stat(int flags) throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_mutex_stat(swigCPtr, this, flags); }

  public int get_tx_max() throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_get_tx_max(swigCPtr, this); }

  public long get_tx_timestamp() throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_get_tx_timestamp(swigCPtr, this); }

  public long get_timeout(int flag) throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_get_timeout(swigCPtr, this, flag); }

  public DbTxn txn_begin(DbTxn parent, int flags) throws com.sleepycat.db.DatabaseException {
    long cPtr = db_javaJNI.DbEnv_txn_begin(swigCPtr, this, DbTxn.getCPtr(parent), parent, flags);
    return (cPtr == 0) ? null : new DbTxn(cPtr, false);
  }

  public void txn_checkpoint(int kbyte, int min, int flags) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_txn_checkpoint(swigCPtr, this, kbyte, min, flags); }

  public com.sleepycat.db.PreparedTransaction[] txn_recover(int count, int flags) throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_txn_recover(swigCPtr, this, count, flags); }

  public com.sleepycat.db.TransactionStats txn_stat(int flags) throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_txn_stat(swigCPtr, this, flags); }

  public long rep_get_limit() throws com.sleepycat.db.DatabaseException {
    return db_javaJNI.DbEnv_rep_get_limit(swigCPtr, this);
  }

  public void rep_elect(int nsites, int nvotes, int flags) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_rep_elect(swigCPtr, this, nsites, nvotes, flags); }

  public int rep_process_message(com.sleepycat.db.DatabaseEntry control, com.sleepycat.db.DatabaseEntry rec, int envid, com.sleepycat.db.LogSequenceNumber ret_lsn) /* no exception */ {
    return db_javaJNI.DbEnv_rep_process_message(swigCPtr, this, control, rec, envid, ret_lsn);
  }

  public void rep_flush() throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_rep_flush(swigCPtr, this); }

  public void rep_set_config(int which, boolean onoff) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_rep_set_config(swigCPtr, this, which, onoff); }

  public void rep_set_clockskew(int fast_clock, int slow_clock) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_rep_set_clockskew(swigCPtr, this, fast_clock, slow_clock); }

  public int rep_get_clockskew_fast() throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_rep_get_clockskew_fast(swigCPtr, this); }

  public int rep_get_clockskew_slow() throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_rep_get_clockskew_slow(swigCPtr, this); }

  public void rep_start(com.sleepycat.db.DatabaseEntry cdata, int flags) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_rep_start(swigCPtr, this, cdata, flags); }

  public void rep_sync(int flags) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_rep_sync(swigCPtr, this, flags); }

  public boolean rep_get_config(int which) throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_rep_get_config(swigCPtr, this, which); }

  public com.sleepycat.db.ReplicationStats rep_stat(int flags) throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_rep_stat(swigCPtr, this, flags); }

  public void rep_set_limit(long bytes) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_rep_set_limit(swigCPtr, this, bytes); }

  public int rep_get_request_min() throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_rep_get_request_min(swigCPtr, this); }

  public int rep_get_request_max() throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_rep_get_request_max(swigCPtr, this); }

  public void rep_set_request(int min, int max) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_rep_set_request(swigCPtr, this, min, max); }

  public void rep_set_transport(int envid, com.sleepycat.db.ReplicationTransport send) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_rep_set_transport(swigCPtr, this, envid,  (rep_transport_handler = send) != null ); }

  public int rep_get_nsites() throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_rep_get_nsites(swigCPtr, this); }

  public int rep_get_priority() throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_rep_get_priority(swigCPtr, this); }

  public int rep_get_timeout(int which) throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_rep_get_timeout(swigCPtr, this, which); }

  public void rep_set_nsites(int number) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_rep_set_nsites(swigCPtr, this, number); }

  public void rep_set_priority(int priority) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_rep_set_priority(swigCPtr, this, priority); }

  public void rep_set_timeout(int which, long timeout) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_rep_set_timeout(swigCPtr, this, which, timeout); }

  public int repmgr_add_remote_site(String host, int port, int flags) throws com.sleepycat.db.DatabaseException {
    return db_javaJNI.DbEnv_repmgr_add_remote_site(swigCPtr, this, host, port, flags);
  }

  public void repmgr_get_ack_policy() throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_repmgr_get_ack_policy(swigCPtr, this); }

  public void repmgr_set_ack_policy(int policy) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_repmgr_set_ack_policy(swigCPtr, this, policy); }

  public void repmgr_set_local_site(String host, int port, int flags) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_repmgr_set_local_site(swigCPtr, this, host, port, flags); }

  public com.sleepycat.db.ReplicationManagerSiteInfo[] repmgr_site_list() throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_repmgr_site_list(swigCPtr, this); }

  public void repmgr_start(int nthreads, int flags) throws com.sleepycat.db.DatabaseException { db_javaJNI.DbEnv_repmgr_start(swigCPtr, this, nthreads, flags); }

  public com.sleepycat.db.ReplicationManagerStats repmgr_stat(int flags) throws com.sleepycat.db.DatabaseException { return db_javaJNI.DbEnv_repmgr_stat(swigCPtr, this, flags); }

  public static String strerror(int error) /* no exception */ {
    return db_javaJNI.DbEnv_strerror(error);
  }

  public static int get_version_major() /* no exception */ {
    return db_javaJNI.DbEnv_get_version_major();
  }

  public static int get_version_minor() /* no exception */ {
    return db_javaJNI.DbEnv_get_version_minor();
  }

  public static int get_version_patch() /* no exception */ {
    return db_javaJNI.DbEnv_get_version_patch();
  }

  public static String get_version_string() /* no exception */ {
    return db_javaJNI.DbEnv_get_version_string();
  }

}
