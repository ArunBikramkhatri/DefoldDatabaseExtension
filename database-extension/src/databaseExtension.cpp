// inAppUpdate.cpp
// Extension lib defines
#define EXTENSION_NAME databaseExtension 
#define LIB_NAME "databaseExtension"
#define MODULE_NAME LIB_NAME

// include the Defold SDK
#include <dmsdk/sdk.h>
// #include <native_activity.h>

#if defined(DM_PLATFORM_ANDROID)

#include <dmsdk/dlib/android.h>

struct DatabaseExtenstion 
{
    jobject        m_DatabaseJNI;
    jmethodID      m_insertData;
    jmethodID      m_getAllUsers;
};

static DatabaseExtenstion  g_database_extenison;



static void CallVoidMethod(jobject instance, jmethodID method)
{
    dmAndroid::ThreadAttacher threadAttacher;
    JNIEnv* env = threadAttacher.GetEnv();

    env->CallVoidMethod(instance, method);
}


static void callVoidMethodCharInt(jobject instance , jmethodID method , const char* cstr , int ci){
    dmLogInfo("working 3")
    dmAndroid::ThreadAttacher threadAttacher;
    JNIEnv* env = threadAttacher.GetEnv();
    jstring str = env->NewStringUTF(cstr);

    env->CallVoidMethod(instance, method, str, ci);
    
    env->DeleteLocalRef(str);
}


static int Database_InsertUser(lua_State* L){
    dmLogInfo("working 0")
    DM_LUA_STACK_CHECK(L, 0);
    if (lua_gettop(L) != 2) {
        luaL_error(L, "Expected 2 arguments");
       
    }
    dmLogInfo("working 1")
    const char* name = luaL_checkstring(L, 1);
    int roll = luaL_checknumber(L, 2);
    // dmLogInfo("working 2")
    // int roll = 10 ;
    callVoidMethodCharInt(g_database_extenison.m_DatabaseJNI , g_database_extenison.m_insertData , name , roll);
    return 0;
}



// Functions exposed to Lua
static const luaL_reg Database_methods[] =
{
    {"insert_user" , Database_InsertUser},
    {0, 0}
};

static void LuaInit(lua_State* L)
{   
    DM_LUA_STACK_CHECK(L, 0);
    luaL_register(L, MODULE_NAME, Database_methods);
    lua_pop(L, 1);
}



static void InitJNIMethods(JNIEnv* env , jclass cls){
   g_database_extenison.m_insertData = env->GetMethodID(cls , "insertData" , "(Ljava/lang/String;I)V");
   g_database_extenison.m_getAllUsers = env->GetMethodID(cls , "getAllUsers" , "()V");
}


void InitializeJNI() {
    dmAndroid::ThreadAttacher threadAttacher;
    JNIEnv* env = threadAttacher.GetEnv();
    jclass cls = dmAndroid::LoadClass(env, "com.rummy.databaseExtension.DatabaseExtension");
    InitJNIMethods(env, cls);
    jobject native_activity = dmGraphics::GetNativeAndroidActivity();
    jmethodID jni_constructor = env->GetMethodID(cls, "<init>", "(Landroid/app/Activity;)V");
    g_database_extenison.m_DatabaseJNI = env->NewGlobalRef(env->NewObject(cls, jni_constructor, native_activity));
}

static dmExtension::Result AppInitializeMyExtension(dmExtension::AppParams* params)
{
    dmLogInfo("AppInitializeMyExtension");
    return dmExtension::RESULT_OK;
}

static dmExtension::Result InitializeMyExtension(dmExtension::Params* params)
{
    // Init Lua
    LuaInit(params->m_L);
    InitializeJNI();
    dmLogInfo("Registered %s Extension", MODULE_NAME);
    return dmExtension::RESULT_OK;
}

static dmExtension::Result AppFinalizeMyExtension(dmExtension::AppParams* params)
{
    dmLogInfo("AppFinalizeMyExtension");
    return dmExtension::RESULT_OK;
}

static dmExtension::Result FinalizeMyExtension(dmExtension::Params* params)
{
    dmLogInfo("FinalizeMyExtension");
    return dmExtension::RESULT_OK;
}

static dmExtension::Result OnUpdateMyExtension(dmExtension::Params* params)
{
    // dmLogInfo("OnUpdateMyExtension");
    return dmExtension::RESULT_OK;
}

static void OnEventMyExtension(dmExtension::Params* params, const dmExtension::Event* event)
{
    switch(event->m_Event)
    {
        case dmExtension::EVENT_ID_ACTIVATEAPP:
        dmLogInfo("OnEventMyExtension - EVENT_ID_ACTIVATEAPP");
        break;
        case dmExtension::EVENT_ID_DEACTIVATEAPP:
        dmLogInfo("OnEventMyExtension - EVENT_ID_DEACTIVATEAPP");
        break;
        case dmExtension::EVENT_ID_ICONIFYAPP:
        dmLogInfo("OnEventMyExtension - EVENT_ID_ICONIFYAPP");
        break;
        case dmExtension::EVENT_ID_DEICONIFYAPP:
        dmLogInfo("OnEventMyExtension - EVENT_ID_DEICONIFYAPP");
        break;
        default:
        dmLogWarning("OnEventMyExtension - Unknown event id");
        break;
    }
}
#else
static dmExtension::Result AppInitializeMyExtension(dmExtension::AppParams* params)
{
    dmLogInfo("AppInitializeMyExtension");
    return dmExtension::RESULT_OK;
}

static dmExtension::Result InitializeMyExtension(dmExtension::Params* params)
{
    dmLogWarning("Registered %s (null) Extension\n", MODULE_NAME);
    return dmExtension::RESULT_OK;
}
static dmExtension::Result AppFinalizeMyExtension(dmExtension::AppParams* params)
{
    dmLogInfo("AppFinalizeMyExtension");
    return dmExtension::RESULT_OK;
}

static dmExtension::Result FinalizeMyExtension(dmExtension::Params* params)
{
    dmLogInfo("FinalizeMyExtension");
    return dmExtension::RESULT_OK;
}

static dmExtension::Result OnUpdateMyExtension(dmExtension::Params* params)
{
    // dmLogInfo("OnUpdateMyExtension");
    return dmExtension::RESULT_OK;
}

static void OnEventMyExtension(dmExtension::Params* params, const dmExtension::Event* event)
{
    switch(event->m_Event)
    {
        case dmExtension::EVENT_ID_ACTIVATEAPP:
        dmLogInfo("OnEventMyExtension - EVENT_ID_ACTIVATEAPP");
        break;
        case dmExtension::EVENT_ID_DEACTIVATEAPP:
        dmLogInfo("OnEventMyExtension - EVENT_ID_DEACTIVATEAPP");
        break;
        case dmExtension::EVENT_ID_ICONIFYAPP:
        dmLogInfo("OnEventMyExtension - EVENT_ID_ICONIFYAPP");
        break;
        case dmExtension::EVENT_ID_DEICONIFYAPP:
        dmLogInfo("OnEventMyExtension - EVENT_ID_DEICONIFYAPP");
        break;
        default:
        dmLogWarning("OnEventMyExtension - Unknown event id");
        break;
    }
}

#endif

// Defold SDK uses a macro for setting up extension entry points:
//
// DM_DECLARE_EXTENSION(symbol, name, app_init, app_final, init, update, on_event, final)

// MyExtension is the C++ symbol that holds all relevant extension data.
// It must match the name field in the `ext.manifest`
DM_DECLARE_EXTENSION(EXTENSION_NAME, LIB_NAME, AppInitializeMyExtension, AppFinalizeMyExtension, InitializeMyExtension, OnUpdateMyExtension, OnEventMyExtension, FinalizeMyExtension)
