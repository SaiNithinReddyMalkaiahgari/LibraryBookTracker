package librarytrackingapp.sainithinreddymalkaiahgari.s3463812

import android.content.Context



object LibraryTrackerPrefs {

    private const val PREF_STORAGE = "LIBRARY_TRACKER_PREF_STORAGE"
    private const val KEY_SESSION_ACTIVE = "KEY_SESSION_ACTIVE"
    private const val KEY_MEMBER_NAME = "KEY_MEMBER_NAME"
    private const val KEY_MEMBER_EMAIL = "KEY_MEMBER_EMAIL"

    fun setSessionActive(context: Context, isActive: Boolean) {
        val prefs = context.getSharedPreferences(PREF_STORAGE, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(KEY_SESSION_ACTIVE, isActive).apply()
    }

    fun isSessionActive(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREF_STORAGE, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_SESSION_ACTIVE, false)
    }

    fun setMemberName(context: Context, name: String) {
        val prefs = context.getSharedPreferences(PREF_STORAGE, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_MEMBER_NAME, name).apply()
    }

    fun getMemberName(context: Context): String {
        val prefs = context.getSharedPreferences(PREF_STORAGE, Context.MODE_PRIVATE)
        return prefs.getString(KEY_MEMBER_NAME, "") ?: ""
    }

    fun setMemberEmail(context: Context, email: String) {
        val prefs = context.getSharedPreferences(PREF_STORAGE, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_MEMBER_EMAIL, email).apply()
    }

    fun getMemberEmail(context: Context): String {
        val prefs = context.getSharedPreferences(PREF_STORAGE, Context.MODE_PRIVATE)
        return prefs.getString(KEY_MEMBER_EMAIL, "") ?: ""
    }
}
