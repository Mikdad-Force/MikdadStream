.class public final Lcom/example/data/network/LocalMovieBoxServer;
.super Ljava/lang/Object;
.source "LocalMovieBoxServer.kt"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/example/data/network/LocalMovieBoxServer$Companion;
    }
.end annotation

.annotation system Ldalvik/annotation/SourceDebugExtension;
    value = "SMAP\nLocalMovieBoxServer.kt\nKotlin\n*S Kotlin\n*F\n+ 1 LocalMovieBoxServer.kt\ncom/example/data/network/LocalMovieBoxServer\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,1788:1\n1617#2,9:1789\n1869#2:1798\n1870#2:1800\n1626#2:1801\n1617#2,9:1802\n1869#2:1811\n1870#2:1813\n1626#2:1814\n1617#2,9:1815\n1869#2:1824\n1870#2:1826\n1626#2:1827\n1617#2,9:1828\n1869#2:1837\n1870#2:1839\n1626#2:1840\n1617#2,9:1841\n1869#2:1850\n1870#2:1852\n1626#2:1853\n1617#2,9:1854\n1869#2:1863\n1870#2:1865\n1626#2:1866\n1617#2,9:1867\n1869#2:1876\n1870#2:1878\n1626#2:1879\n1869#2,2:1881\n1617#2,9:1883\n1869#2:1892\n1870#2:1894\n1626#2:1895\n1869#2,2:1897\n1563#2:1899\n1634#2,3:1900\n1761#2,3:1903\n1869#2,2:1906\n1617#2,9:1908\n1869#2:1917\n1870#2:1919\n1626#2:1920\n1617#2,9:1922\n1869#2:1931\n1870#2:1933\n1626#2:1934\n1056#2:1936\n1869#2,2:1941\n1869#2,2:1945\n1#3:1799\n1#3:1812\n1#3:1825\n1#3:1838\n1#3:1851\n1#3:1864\n1#3:1877\n1#3:1880\n1#3:1893\n1#3:1896\n1#3:1918\n1#3:1921\n1#3:1932\n1#3:1935\n216#4,2:1937\n216#4,2:1939\n216#4,2:1943\n*S KotlinDebug\n*F\n+ 1 LocalMovieBoxServer.kt\ncom/example/data/network/LocalMovieBoxServer\n*L\n460#1:1789,9\n460#1:1798\n460#1:1800\n460#1:1801\n484#1:1802,9\n484#1:1811\n484#1:1813\n484#1:1814\n516#1:1815,9\n516#1:1824\n516#1:1826\n516#1:1827\n548#1:1828,9\n548#1:1837\n548#1:1839\n548#1:1840\n563#1:1841,9\n563#1:1850\n563#1:1852\n563#1:1853\n575#1:1854,9\n575#1:1863\n575#1:1865\n575#1:1866\n599#1:1867,9\n599#1:1876\n599#1:1878\n599#1:1879\n634#1:1881,2\n653#1:1883,9\n653#1:1892\n653#1:1894\n653#1:1895\n733#1:1897,2\n934#1:1899\n934#1:1900,3\n1045#1:1903,3\n1174#1:1906,2\n1195#1:1908,9\n1195#1:1917\n1195#1:1919\n1195#1:1920\n1246#1:1922,9\n1246#1:1931\n1246#1:1933\n1246#1:1934\n1348#1:1936\n1716#1:1941,2\n1738#1:1945,2\n460#1:1799\n484#1:1812\n516#1:1825\n548#1:1838\n563#1:1851\n575#1:1864\n599#1:1877\n653#1:1893\n1195#1:1918\n1246#1:1932\n1381#1:1937,2\n1707#1:1939,2\n1729#1:1943,2\n*E\n"
.end annotation

.annotation runtime Lkotlin/Metadata;
    d1 = {
        "\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0008\n\u0002\u0008\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0008\u0002\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0008\u0003\n\u0002\u0018\u0002\n\u0002\u0008\u000b\n\u0002\u0010 \n\u0002\u0008\r\n\u0002\u0010\u0012\n\u0002\u0008\u000b\n\u0002\u0018\u0002\n\u0002\u0008\u0002\n\u0002\u0018\u0002\n\u0002\u0008\u0002\u0008\u0007\u0018\u0000 L2\u00020\u0001:\u0001LB\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0008\u0008\u0002\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0004\u0008\u0006\u0010\u0007J\u0006\u0010\u001d\u001a\u00020\u001eJ\u0006\u0010\u001f\u001a\u00020\u001eJ\u0016\u0010 \u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\"H\u0082@\u00a2\u0006\u0002\u0010#J*\u0010$\u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\"2\u0006\u0010%\u001a\u00020\u00052\u0006\u0010&\u001a\u00020\u00182\u0008\u0008\u0002\u0010\'\u001a\u00020\u0018H\u0002J4\u0010(\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00180\u00192\u0006\u0010)\u001a\u00020\u00182\u0006\u0010*\u001a\u00020\u00182\u0006\u0010+\u001a\u00020\u00182\u0006\u0010&\u001a\u00020\u0018H\u0002J\u001c\u0010,\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00180\u00132\u0006\u0010+\u001a\u00020\u0018H\u0002J8\u0010-\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u00130.2\u001a\u0010/\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u00130.H\u0002J.\u00100\u001a\u0012\u0012\u0004\u0012\u00020\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u0001\u0018\u00010\u00132\u0014\u00101\u001a\u0010\u0012\u0004\u0012\u00020\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0013H\u0002J8\u00102\u001a\u00020\u00182\u0006\u0010)\u001a\u00020\u00182\u0006\u00103\u001a\u00020\u00182\u0014\u0008\u0002\u00104\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00180\u00132\u0008\u0008\u0002\u00105\u001a\u00020\u0018H\u0002J\u0008\u00106\u001a\u00020\u001eH\u0002J\u0008\u00107\u001a\u00020\u0018H\u0002J\u0010\u00108\u001a\u00020\u00182\u0006\u00109\u001a\u00020\u0018H\u0002J\u0010\u0010:\u001a\u00020\u00182\u0006\u0010;\u001a\u00020<H\u0002J\u0018\u0010=\u001a\u00020<2\u0006\u0010>\u001a\u00020<2\u0006\u0010?\u001a\u00020<H\u0002J\u001c\u0010@\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u00130.H\u0002J\u0012\u0010A\u001a\u00020\u00182\u0008\u0010B\u001a\u0004\u0018\u00010\u0001H\u0002J\u0012\u0010C\u001a\u00020\u00012\u0008\u0010B\u001a\u0004\u0018\u00010\u0001H\u0002J\u001e\u0010D\u001a\u0010\u0012\u0004\u0012\u00020\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u00132\u0006\u0010E\u001a\u00020\u0018H\u0002J\u001e\u0010F\u001a\u0010\u0012\u0004\u0012\u00020\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u00132\u0006\u0010G\u001a\u00020HH\u0002J\u0018\u0010I\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010.2\u0006\u0010J\u001a\u00020KH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0008\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000c\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000e\u001a\n \u0010*\u0004\u0018\u00010\u000f0\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000RR\u0010\u0011\u001aF\u0012\u001c\u0012\u001a\u0012\u0002\u0008\u0003\u0012\u0002\u0008\u0003 \u0010*\u000c\u0012\u0002\u0008\u0003\u0012\u0002\u0008\u0003\u0018\u00010\u00130\u0013 \u0010*\"\u0012\u001c\u0012\u001a\u0012\u0002\u0008\u0003\u0012\u0002\u0008\u0003 \u0010*\u000c\u0012\u0002\u0008\u0003\u0012\u0002\u0008\u0003\u0018\u00010\u00130\u0013\u0018\u00010\u00120\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R&\u0010\u0016\u001a\u001a\u0012\u0004\u0012\u00020\u0018\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u00180\u00190\u0017X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u001b\u001a\n \u0010*\u0004\u0018\u00010\u001c0\u001cX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006M"
    }
    d2 = {
        "Lcom/example/data/network/LocalMovieBoxServer;",
        "",
        "context",
        "Landroid/content/Context;",
        "port",
        "",
        "<init>",
        "(Landroid/content/Context;I)V",
        "serverSocket",
        "Ljava/net/ServerSocket;",
        "scope",
        "Lkotlinx/coroutines/CoroutineScope;",
        "isRunning",
        "",
        "moshi",
        "Lcom/squareup/moshi/Moshi;",
        "kotlin.jvm.PlatformType",
        "mapAdapter",
        "Lcom/squareup/moshi/JsonAdapter;",
        "",
        "okHttpClient",
        "Lokhttp3/OkHttpClient;",
        "responseCache",
        "Ljava/util/concurrent/ConcurrentHashMap;",
        "",
        "Lkotlin/Pair;",
        "",
        "diskCachePrefs",
        "Landroid/content/SharedPreferences;",
        "start",
        "",
        "stop",
        "handleClient",
        "socket",
        "Ljava/net/Socket;",
        "(Ljava/net/Socket;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;",
        "sendResponse",
        "statusCode",
        "body",
        "contentType",
        "routeRequest",
        "method",
        "path",
        "query",
        "parseQueryParams",
        "formatTabSections",
        "",
        "items",
        "mapItem",
        "item",
        "makeOfficialRequest",
        "endpoint",
        "queryParams",
        "bodyString",
        "bootstrapGuestToken",
        "getDeviceId",
        "md5",
        "input",
        "md5Bytes",
        "bytes",
        "",
        "hmacMd5",
        "keyBytes",
        "dataBytes",
        "getMockTrending",
        "toJsonString",
        "obj",
        "toJsonElement",
        "parseJsonToMap",
        "jsonStr",
        "jsonToMap",
        "json",
        "Lorg/json/JSONObject;",
        "jsonToList",
        "array",
        "Lorg/json/JSONArray;",
        "Companion",
        "app"
    }
    k = 0x1
    mv = {
        0x2,
        0x2,
        0x0
    }
    xi = 0x30
.end annotation


# static fields
.field public static final $stable:I

.field public static final Companion:Lcom/example/data/network/LocalMovieBoxServer$Companion;

.field private static volatile guestToken:Ljava/lang/String;


# instance fields
.field private final context:Landroid/content/Context;

.field private final diskCachePrefs:Landroid/content/SharedPreferences;

.field private isRunning:Z

.field private final mapAdapter:Lcom/squareup/moshi/JsonAdapter;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Lcom/squareup/moshi/JsonAdapter<",
            "Ljava/util/Map<",
            "**>;>;"
        }
    .end annotation
.end field

.field private final moshi:Lcom/squareup/moshi/Moshi;

.field private final okHttpClient:Lokhttp3/OkHttpClient;

.field private final port:I

.field private final responseCache:Ljava/util/concurrent/ConcurrentHashMap;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/concurrent/ConcurrentHashMap<",
            "Ljava/lang/String;",
            "Lkotlin/Pair<",
            "Ljava/lang/Long;",
            "Ljava/lang/String;",
            ">;>;"
        }
    .end annotation
.end field

.field private final scope:Lkotlinx/coroutines/CoroutineScope;

.field private serverSocket:Ljava/net/ServerSocket;


# direct methods
.method static constructor <clinit>()V
    .registers 2

    new-instance v0, Lcom/example/data/network/LocalMovieBoxServer$Companion;

    const/4 v1, 0x0

    invoke-direct {v0, v1}, Lcom/example/data/network/LocalMovieBoxServer$Companion;-><init>(Lkotlin/jvm/internal/DefaultConstructorMarker;)V

    sput-object v0, Lcom/example/data/network/LocalMovieBoxServer;->Companion:Lcom/example/data/network/LocalMovieBoxServer$Companion;

    const/16 v0, 0x8

    sput v0, Lcom/example/data/network/LocalMovieBoxServer;->$stable:I

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;I)V
    .registers 9

    const-string v0, "context"

    invoke-static {p1, v0}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullParameter(Ljava/lang/Object;Ljava/lang/String;)V

    .line 25
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/example/data/network/LocalMovieBoxServer;->context:Landroid/content/Context;

    iput p2, p0, Lcom/example/data/network/LocalMovieBoxServer;->port:I

    .line 31
    invoke-static {}, Lkotlinx/coroutines/Dispatchers;->getIO()Lkotlinx/coroutines/CoroutineDispatcher;

    move-result-object p2

    const/4 v0, 0x0

    const/4 v1, 0x1

    invoke-static {v0, v1, v0}, Lkotlinx/coroutines/SupervisorKt;->SupervisorJob$default(Lkotlinx/coroutines/Job;ILjava/lang/Object;)Lkotlinx/coroutines/CompletableJob;

    move-result-object v0

    check-cast v0, Lkotlin/coroutines/CoroutineContext;

    invoke-virtual {p2, v0}, Lkotlinx/coroutines/CoroutineDispatcher;->plus(Lkotlin/coroutines/CoroutineContext;)Lkotlin/coroutines/CoroutineContext;

    move-result-object p2

    invoke-static {p2}, Lkotlinx/coroutines/CoroutineScopeKt;->CoroutineScope(Lkotlin/coroutines/CoroutineContext;)Lkotlinx/coroutines/CoroutineScope;

    move-result-object p2

    iput-object p2, p0, Lcom/example/data/network/LocalMovieBoxServer;->scope:Lkotlinx/coroutines/CoroutineScope;

    .line 33
    new-instance p2, Lcom/squareup/moshi/Moshi$Builder;

    invoke-direct {p2}, Lcom/squareup/moshi/Moshi$Builder;-><init>()V

    invoke-virtual {p2}, Lcom/squareup/moshi/Moshi$Builder;->build()Lcom/squareup/moshi/Moshi;

    move-result-object p2

    iput-object p2, p0, Lcom/example/data/network/LocalMovieBoxServer;->moshi:Lcom/squareup/moshi/Moshi;

    .line 34
    const-class v0, Ljava/util/Map;

    invoke-virtual {p2, v0}, Lcom/squareup/moshi/Moshi;->adapter(Ljava/lang/Class;)Lcom/squareup/moshi/JsonAdapter;

    move-result-object p2

    iput-object p2, p0, Lcom/example/data/network/LocalMovieBoxServer;->mapAdapter:Lcom/squareup/moshi/JsonAdapter;

    .line 36
    new-instance p2, Lokhttp3/OkHttpClient$Builder;

    invoke-direct {p2}, Lokhttp3/OkHttpClient$Builder;-><init>()V

    .line 37
    new-instance v0, Lokhttp3/ConnectionPool;

    const-wide/16 v2, 0x5

    sget-object v4, Ljava/util/concurrent/TimeUnit;->MINUTES:Ljava/util/concurrent/TimeUnit;

    const/16 v5, 0xa

    invoke-direct {v0, v5, v2, v3, v4}, Lokhttp3/ConnectionPool;-><init>(IJLjava/util/concurrent/TimeUnit;)V

    invoke-virtual {p2, v0}, Lokhttp3/OkHttpClient$Builder;->connectionPool(Lokhttp3/ConnectionPool;)Lokhttp3/OkHttpClient$Builder;

    move-result-object p2

    const-wide/16 v2, 0x6

    .line 38
    sget-object v0, Ljava/util/concurrent/TimeUnit;->SECONDS:Ljava/util/concurrent/TimeUnit;

    invoke-virtual {p2, v2, v3, v0}, Lokhttp3/OkHttpClient$Builder;->connectTimeout(JLjava/util/concurrent/TimeUnit;)Lokhttp3/OkHttpClient$Builder;

    move-result-object p2

    const-wide/16 v2, 0x8

    .line 39
    sget-object v0, Ljava/util/concurrent/TimeUnit;->SECONDS:Ljava/util/concurrent/TimeUnit;

    invoke-virtual {p2, v2, v3, v0}, Lokhttp3/OkHttpClient$Builder;->readTimeout(JLjava/util/concurrent/TimeUnit;)Lokhttp3/OkHttpClient$Builder;

    move-result-object p2

    .line 40
    invoke-virtual {p2, v1}, Lokhttp3/OkHttpClient$Builder;->retryOnConnectionFailure(Z)Lokhttp3/OkHttpClient$Builder;

    move-result-object p2

    .line 41
    invoke-virtual {p2}, Lokhttp3/OkHttpClient$Builder;->build()Lokhttp3/OkHttpClient;

    move-result-object p2

    iput-object p2, p0, Lcom/example/data/network/LocalMovieBoxServer;->okHttpClient:Lokhttp3/OkHttpClient;

    .line 43
    new-instance p2, Ljava/util/concurrent/ConcurrentHashMap;

    invoke-direct {p2}, Ljava/util/concurrent/ConcurrentHashMap;-><init>()V

    iput-object p2, p0, Lcom/example/data/network/LocalMovieBoxServer;->responseCache:Ljava/util/concurrent/ConcurrentHashMap;

    .line 44
    const-string p2, "moviebox_api_cache"

    const/4 v0, 0x0

    invoke-virtual {p1, p2, v0}, Landroid/content/Context;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object p1

    iput-object p1, p0, Lcom/example/data/network/LocalMovieBoxServer;->diskCachePrefs:Landroid/content/SharedPreferences;

    return-void
.end method

.method public synthetic constructor <init>(Landroid/content/Context;IILkotlin/jvm/internal/DefaultConstructorMarker;)V
    .registers 5

    and-int/lit8 p3, p3, 0x2

    if-eqz p3, :cond_6

    const/16 p2, 0xbb8

    .line 25
    :cond_6
    invoke-direct {p0, p1, p2}, Lcom/example/data/network/LocalMovieBoxServer;-><init>(Landroid/content/Context;I)V

    return-void
.end method

.method public static final synthetic access$getPort$p(Lcom/example/data/network/LocalMovieBoxServer;)I
    .registers 1

    .line 25
    iget p0, p0, Lcom/example/data/network/LocalMovieBoxServer;->port:I

    return p0
.end method

.method public static final synthetic access$getServerSocket$p(Lcom/example/data/network/LocalMovieBoxServer;)Ljava/net/ServerSocket;
    .registers 1

    .line 25
    iget-object p0, p0, Lcom/example/data/network/LocalMovieBoxServer;->serverSocket:Ljava/net/ServerSocket;

    return-object p0
.end method

.method public static final synthetic access$handleClient(Lcom/example/data/network/LocalMovieBoxServer;Ljava/net/Socket;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;
    .registers 3

    .line 25
    invoke-direct {p0, p1, p2}, Lcom/example/data/network/LocalMovieBoxServer;->handleClient(Ljava/net/Socket;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;

    move-result-object p0

    return-object p0
.end method

.method public static final synthetic access$isRunning$p(Lcom/example/data/network/LocalMovieBoxServer;)Z
    .registers 1

    .line 25
    iget-boolean p0, p0, Lcom/example/data/network/LocalMovieBoxServer;->isRunning:Z

    return p0
.end method

.method public static final synthetic access$routeRequest(Lcom/example/data/network/LocalMovieBoxServer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lkotlin/Pair;
    .registers 5

    .line 25
    invoke-direct {p0, p1, p2, p3, p4}, Lcom/example/data/network/LocalMovieBoxServer;->routeRequest(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lkotlin/Pair;

    move-result-object p0

    return-object p0
.end method

.method public static final synthetic access$sendResponse(Lcom/example/data/network/LocalMovieBoxServer;Ljava/net/Socket;ILjava/lang/String;Ljava/lang/String;)V
    .registers 5

    .line 25
    invoke-direct {p0, p1, p2, p3, p4}, Lcom/example/data/network/LocalMovieBoxServer;->sendResponse(Ljava/net/Socket;ILjava/lang/String;Ljava/lang/String;)V

    return-void
.end method

.method public static final synthetic access$setServerSocket$p(Lcom/example/data/network/LocalMovieBoxServer;Ljava/net/ServerSocket;)V
    .registers 2

    .line 25
    iput-object p1, p0, Lcom/example/data/network/LocalMovieBoxServer;->serverSocket:Ljava/net/ServerSocket;

    return-void
.end method

.method private final bootstrapGuestToken()V
    .registers 25

    move-object/from16 v0, p0

    .line 1517
    const-string v1, "region"

    const-string v2, "en"

    const-string v3, "googleplay"

    const-string v4, "android"

    const-string v5, "os"

    const-string v6, "sp_code"

    const-string v7, "getBytes(...)"

    const-string v8, ""

    const-string v9, "LocalMovieBoxServer"

    .line 0
    const-string v10, "Successfully bootstrapped dynamic guest token: "

    const-string v11, "Failed to retrieve guest token from response headers. Status: "

    const-string v12, "GET\napplication/json\napplication/json;charset=UTF-8\n\n"

    .line 1518
    :try_start_1a
    const-string v13, "Bootstrapping guest credentials..."

    invoke-static {v9, v13}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 1519
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v13

    invoke-static {v13, v14}, Ljava/lang/String;->valueOf(J)Ljava/lang/String;

    move-result-object v13

    .line 1520
    move-object v14, v13

    check-cast v14, Ljava/lang/CharSequence;

    invoke-static {v14}, Lkotlin/text/StringsKt;->reversed(Ljava/lang/CharSequence;)Ljava/lang/CharSequence;

    move-result-object v14

    invoke-virtual {v14}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v14

    .line 1521
    invoke-direct {v0, v14}, Lcom/example/data/network/LocalMovieBoxServer;->md5(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v14

    new-instance v15, Ljava/lang/StringBuilder;

    invoke-direct {v15}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v15, v13}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v15

    move-object/from16 v16, v11

    const-string v11, ","

    invoke-virtual {v15, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    invoke-virtual {v11, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    invoke-virtual {v11}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v11

    .line 1525
    const-string v14, "https://api.inmoviebox.com/wefeed-mobile-bff/tab-operating?host=api.inmoviebox.com&page=1&pageSize=24&tabId=1"

    .line 1527
    new-instance v15, Ljava/lang/StringBuilder;

    invoke-direct {v15, v12}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v15, v13}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v12

    const-string v15, "\n\n/wefeed-mobile-bff/tab-operating?host=api.inmoviebox.com&page=1&pageSize=24&tabId=1"

    invoke-virtual {v12, v15}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v12

    invoke-virtual {v12}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v12

    .line 1530
    const-string v15, "76iRl07s0xSN9jqmEWAt79EBJZulIQIsV64FZr2O"
    :try_end_66
    .catch Ljava/lang/Exception; {:try_start_1a .. :try_end_66} :catch_2e6

    move-object/from16 v17, v9

    const/4 v9, 0x0

    .line 1532
    :try_start_69
    invoke-static {v15, v9}, Landroid/util/Base64;->decode(Ljava/lang/String;I)[B

    move-result-object v15
    :try_end_6d
    .catch Ljava/lang/Exception; {:try_start_69 .. :try_end_6d} :catch_6e

    goto :goto_77

    .line 1534
    :catch_6e
    :try_start_6e
    sget-object v9, Lkotlin/text/Charsets;->UTF_8:Ljava/nio/charset/Charset;

    invoke-virtual {v15, v9}, Ljava/lang/String;->getBytes(Ljava/nio/charset/Charset;)[B

    move-result-object v15

    invoke-static {v15, v7}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V

    .line 1536
    :goto_77
    invoke-static {v15}, Lkotlin/jvm/internal/Intrinsics;->checkNotNull(Ljava/lang/Object;)V

    sget-object v9, Lkotlin/text/Charsets;->UTF_8:Ljava/nio/charset/Charset;

    invoke-virtual {v12, v9}, Ljava/lang/String;->getBytes(Ljava/nio/charset/Charset;)[B

    move-result-object v9

    invoke-static {v9, v7}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V

    invoke-direct {v0, v15, v9}, Lcom/example/data/network/LocalMovieBoxServer;->hmacMd5([B[B)[B

    move-result-object v7

    const/4 v9, 0x2

    .line 1537
    invoke-static {v7, v9}, Landroid/util/Base64;->encodeToString([BI)Ljava/lang/String;

    move-result-object v7

    .line 1538
    new-instance v12, Ljava/lang/StringBuilder;

    invoke-direct {v12}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v12, v13}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v12

    const-string v15, "|2|"

    invoke-virtual {v12, v15}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v12

    invoke-virtual {v12, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    .line 1540
    iget-object v12, v0, Lcom/example/data/network/LocalMovieBoxServer;->context:Landroid/content/Context;

    const-string v15, "moviebox_prefs"

    move/from16 v19, v9

    const/4 v9, 0x0

    invoke-virtual {v12, v15, v9}, Landroid/content/Context;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v12

    .line 1541
    invoke-interface {v12, v6, v8}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v9

    if-eqz v9, :cond_c2

    move-object/from16 v20, v9

    check-cast v20, Ljava/lang/CharSequence;

    invoke-interface/range {v20 .. v20}, Ljava/lang/CharSequence;->length()I

    move-result v20

    if-lez v20, :cond_bf

    goto :goto_c0

    :cond_bf
    const/4 v9, 0x0

    :goto_c0
    if-nez v9, :cond_c4

    :cond_c2
    const-string v9, "404"

    .line 1542
    :cond_c4
    const-string v15, "custom_local_iso"

    invoke-interface {v12, v15, v8}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v12

    if-eqz v12, :cond_e6

    move-object v15, v12

    check-cast v15, Ljava/lang/CharSequence;

    invoke-interface {v15}, Ljava/lang/CharSequence;->length()I

    move-result v15

    if-lez v15, :cond_d6

    goto :goto_d7

    :cond_d6
    const/4 v12, 0x0

    :goto_d7
    if-eqz v12, :cond_e6

    sget-object v15, Ljava/util/Locale;->ROOT:Ljava/util/Locale;

    invoke-virtual {v12, v15}, Ljava/lang/String;->toUpperCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object v12

    const-string v15, "toUpperCase(...)"

    invoke-static {v12, v15}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V

    if-nez v12, :cond_e8

    :cond_e6
    const-string v12, "IN"

    .line 1543
    :cond_e8
    invoke-virtual {v12}, Ljava/lang/String;->hashCode()I

    move-result v15

    move-object/from16 v21, v10

    const/16 v10, 0x850

    if-eq v15, v10, :cond_112

    const/16 v10, 0xa43

    if-eq v15, v10, :cond_106

    const/16 v10, 0xa9e

    if-eq v15, v10, :cond_fb

    goto :goto_11a

    :cond_fb
    const-string v10, "US"

    invoke-virtual {v12, v10}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v10

    if-eqz v10, :cond_11a

    const-string v10, "America/New_York"

    goto :goto_11f

    :cond_106
    const-string v10, "RU"

    invoke-virtual {v12, v10}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v10

    if-nez v10, :cond_10f

    goto :goto_11a

    :cond_10f
    const-string v10, "Europe/Moscow"

    goto :goto_11f

    :cond_112
    const-string v10, "BR"

    invoke-virtual {v12, v10}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v10

    if-nez v10, :cond_11d

    :cond_11a
    :goto_11a
    const-string v10, "Asia/Kolkata"

    goto :goto_11f

    :cond_11d
    const-string v10, "America/Sao_Paulo"

    :goto_11f
    const/16 v15, 0x10

    .line 1546
    new-array v15, v15, [Lkotlin/Pair;

    move-object/from16 v22, v15

    const-string v15, "package_name"

    move-object/from16 v23, v7

    const-string v7, "com.movieboxpro.android"

    invoke-static {v15, v7}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v7

    const/16 v18, 0x0

    aput-object v7, v22, v18

    .line 1547
    const-string v7, "version_name"

    const-string v15, "16.2.1"

    invoke-static {v7, v15}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v7

    const/4 v15, 0x1

    aput-object v7, v22, v15

    .line 1548
    const-string v7, "version_code"

    const/16 v15, 0x3f52

    invoke-static {v15}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v15

    invoke-static {v7, v15}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v7

    aput-object v7, v22, v19

    .line 1549
    invoke-static {v5, v4}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v7

    const/4 v15, 0x3

    aput-object v7, v22, v15

    .line 1550
    const-string v7, "os_version"

    const-string v15, "12"

    invoke-static {v7, v15}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v7

    const/4 v15, 0x4

    aput-object v7, v22, v15

    .line 1551
    const-string v7, "install_ch"

    invoke-static {v7, v3}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v7

    const/4 v15, 0x5

    aput-object v7, v22, v15

    .line 1552
    const-string v7, "device_id"

    invoke-direct {v0}, Lcom/example/data/network/LocalMovieBoxServer;->getDeviceId()Ljava/lang/String;

    move-result-object v15

    invoke-static {v7, v15}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v7

    const/4 v15, 0x6

    aput-object v7, v22, v15

    .line 1553
    const-string v7, "install_store"

    invoke-static {v7, v3}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v3

    const/4 v7, 0x7

    aput-object v3, v22, v7

    .line 1554
    const-string v3, "gaid"

    invoke-static {v3, v8}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v3

    const/16 v7, 0x8

    aput-object v3, v22, v7

    .line 1555
    const-string v3, "brand"

    const-string v7, "google"

    invoke-static {v3, v7}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v3

    const/16 v7, 0x9

    aput-object v3, v22, v7

    .line 1556
    const-string v3, "model"

    const-string v7, "Pixel 6"

    invoke-static {v3, v7}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v3

    const/16 v7, 0xa

    aput-object v3, v22, v7

    .line 1557
    const-string v3, "system_language"

    invoke-static {v3, v2}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v3

    const/16 v7, 0xb

    aput-object v3, v22, v7

    .line 1558
    const-string v3, "net"

    const-string v7, "wifi"

    invoke-static {v3, v7}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v3

    const/16 v7, 0xc

    aput-object v3, v22, v7

    .line 1559
    invoke-static {v1, v12}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v3

    const/16 v7, 0xd

    aput-object v3, v22, v7

    .line 1560
    const-string v3, "timezone"

    invoke-static {v3, v10}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v3

    const/16 v7, 0xe

    aput-object v3, v22, v7

    .line 1561
    invoke-static {v6, v9}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v3

    const/16 v6, 0xf

    aput-object v3, v22, v6

    .line 1545
    invoke-static/range {v22 .. v22}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v3

    .line 1563
    invoke-direct {v0, v3}, Lcom/example/data/network/LocalMovieBoxServer;->toJsonString(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v3

    .line 1565
    new-instance v6, Lokhttp3/Request$Builder;

    invoke-direct {v6}, Lokhttp3/Request$Builder;-><init>()V

    .line 1566
    invoke-virtual {v6, v14}, Lokhttp3/Request$Builder;->url(Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v6

    .line 1567
    const-string v7, "User-Agent"

    const-string v8, "MovieBoxPro/16.2.1 (Android 14; com.community.mbox.in)"

    invoke-virtual {v6, v7, v8}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v6

    .line 1568
    const-string v7, "Accept"

    const-string v8, "application/json"

    invoke-virtual {v6, v7, v8}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v6

    .line 1569
    const-string v7, "Content-Type"

    const-string v8, "application/json;charset=UTF-8"

    invoke-virtual {v6, v7, v8}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v6

    .line 1570
    const-string v7, "X-Sign-Version"

    const-string v8, "2.0"

    invoke-virtual {v6, v7, v8}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v6

    .line 1571
    const-string v7, "appid"

    const-string v8, "4U01pxRu278GqCZKY9"

    invoke-virtual {v6, v7, v8}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v6

    .line 1572
    invoke-virtual {v6, v1, v12}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v1

    .line 1573
    const-string v6, "lang"

    invoke-virtual {v1, v6, v2}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v1

    .line 1574
    invoke-virtual {v1, v5, v4}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v1

    .line 1575
    const-string v2, "X-Timestamp"

    invoke-virtual {v1, v2, v13}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v1

    .line 1576
    const-string v2, "Referer"

    const-string v4, "https://api.inmoviebox.com/"

    invoke-virtual {v1, v2, v4}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v1

    .line 1577
    const-string v2, "X-Client-Token"

    invoke-virtual {v1, v2, v11}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v1

    .line 1578
    const-string v2, "x-tr-signature"

    move-object/from16 v4, v23

    invoke-virtual {v1, v2, v4}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v1

    .line 1579
    const-string v2, "X-Play-Mode"

    const-string v4, "2"

    invoke-virtual {v1, v2, v4}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v1

    .line 1580
    const-string v2, "X-Client-Info"

    invoke-virtual {v1, v2, v3}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v1

    .line 1581
    const-string v2, "X-Client-Status"

    const-string v3, "1"

    invoke-virtual {v1, v2, v3}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v1

    .line 1582
    invoke-virtual {v1}, Lokhttp3/Request$Builder;->build()Lokhttp3/Request;

    move-result-object v1

    .line 1584
    iget-object v0, v0, Lcom/example/data/network/LocalMovieBoxServer;->okHttpClient:Lokhttp3/OkHttpClient;

    invoke-virtual {v0, v1}, Lokhttp3/OkHttpClient;->newCall(Lokhttp3/Request;)Lokhttp3/Call;

    move-result-object v0

    invoke-interface {v0}, Lokhttp3/Call;->execute()Lokhttp3/Response;

    move-result-object v0

    .line 1585
    const-string v1, "x-user"

    move/from16 v2, v19

    const/4 v3, 0x0

    invoke-static {v0, v1, v3, v2, v3}, Lokhttp3/Response;->header$default(Lokhttp3/Response;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;

    move-result-object v1

    if-nez v1, :cond_267

    const-string v1, "X-User"

    invoke-static {v0, v1, v3, v2, v3}, Lokhttp3/Response;->header$default(Lokhttp3/Response;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;

    move-result-object v1

    .line 1586
    :cond_267
    move-object v2, v1

    check-cast v2, Ljava/lang/CharSequence;

    if-eqz v2, :cond_2c4

    invoke-interface {v2}, Ljava/lang/CharSequence;->length()I

    move-result v2

    if-nez v2, :cond_273

    goto :goto_2c4

    .line 1587
    :cond_273
    const-string v2, "{"

    const/4 v3, 0x2

    const/4 v4, 0x0

    const/4 v9, 0x0

    invoke-static {v1, v2, v9, v3, v4}, Lkotlin/text/StringsKt;->startsWith$default(Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_2ab

    .line 1588
    const-string v4, "\"token\":\""

    .line 1589
    move-object v3, v1

    check-cast v3, Ljava/lang/CharSequence;

    const/4 v7, 0x6

    const/4 v8, 0x0

    const/4 v5, 0x0

    const/4 v6, 0x0

    invoke-static/range {v3 .. v8}, Lkotlin/text/StringsKt;->indexOf$default(Ljava/lang/CharSequence;Ljava/lang/String;IZILjava/lang/Object;)I

    move-result v2

    const/4 v3, -0x1

    if-eq v2, v3, :cond_2ab

    .line 1591
    invoke-virtual {v4}, Ljava/lang/String;->length()I

    move-result v4

    add-int v7, v2, v4

    .line 1592
    move-object v5, v1

    check-cast v5, Ljava/lang/CharSequence;

    const-string v6, "\""

    const/4 v9, 0x4

    const/4 v10, 0x0

    const/4 v8, 0x0

    invoke-static/range {v5 .. v10}, Lkotlin/text/StringsKt;->indexOf$default(Ljava/lang/CharSequence;Ljava/lang/String;IZILjava/lang/Object;)I

    move-result v2

    if-eq v2, v3, :cond_2ab

    .line 1593
    invoke-virtual {v1, v7, v2}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v1

    const-string v2, "substring(...)"

    invoke-static {v1, v2}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V

    .line 1598
    :cond_2ab
    sput-object v1, Lcom/example/data/network/LocalMovieBoxServer;->guestToken:Ljava/lang/String;

    .line 1599
    sget-object v1, Lcom/example/data/network/LocalMovieBoxServer;->guestToken:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    move-object/from16 v3, v21

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1
    :try_end_2be
    .catch Ljava/lang/Exception; {:try_start_6e .. :try_end_2be} :catch_2e2

    move-object/from16 v2, v17

    :try_start_2c0
    invoke-static {v2, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_2dc

    :cond_2c4
    :goto_2c4
    move-object/from16 v2, v17

    .line 1601
    invoke-virtual {v0}, Lokhttp3/Response;->code()I

    move-result v1

    new-instance v3, Ljava/lang/StringBuilder;

    move-object/from16 v4, v16

    invoke-direct {v3, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v2, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 1603
    :goto_2dc
    invoke-virtual {v0}, Lokhttp3/Response;->close()V
    :try_end_2df
    .catch Ljava/lang/Exception; {:try_start_2c0 .. :try_end_2df} :catch_2e0

    goto :goto_2ef

    :catch_2e0
    move-exception v0

    goto :goto_2e8

    :catch_2e2
    move-exception v0

    move-object/from16 v2, v17

    goto :goto_2e8

    :catch_2e6
    move-exception v0

    move-object v2, v9

    .line 1605
    :goto_2e8
    const-string v1, "Guest bootstrap exception"

    check-cast v0, Ljava/lang/Throwable;

    invoke-static {v2, v1, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    :goto_2ef
    return-void
.end method

.method private final formatTabSections(Ljava/util/List;)Ljava/util/List;
    .registers 26
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "+",
            "Ljava/util/Map<",
            "Ljava/lang/String;",
            "+",
            "Ljava/lang/Object;",
            ">;>;)",
            "Ljava/util/List<",
            "Ljava/util/Map<",
            "Ljava/lang/String;",
            "Ljava/lang/Object;",
            ">;>;"
        }
    .end annotation

    move-object/from16 v0, p0

    .line 1184
    new-instance v1, Ljava/util/ArrayList;

    invoke-direct {v1}, Ljava/util/ArrayList;-><init>()V

    check-cast v1, Ljava/util/List;

    .line 1187
    invoke-interface/range {p1 .. p1}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :cond_d
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    const-string v5, "SUBJECTS_MOVIE"

    const/4 v7, 0x3

    const-string v8, "rankingListData"

    const-string v9, "type"

    const-string v10, "banner"

    const-string v11, "customData"

    const/4 v12, 0x1

    const-string v13, "movieList"

    const-string v14, "title"

    const-string v15, "subjects"

    const/16 v16, 0x2

    const-string v4, "list"

    const/16 v17, 0x0

    const-string v6, "items"

    if-eqz v3, :cond_5e

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/util/Map;

    .line 1188
    invoke-interface {v3, v4}, Ljava/util/Map;->containsKey(Ljava/lang/Object;)Z

    move-result v18

    if-nez v18, :cond_ae

    invoke-interface {v3, v6}, Ljava/util/Map;->containsKey(Ljava/lang/Object;)Z

    move-result v18

    if-nez v18, :cond_ae

    invoke-interface {v3, v15}, Ljava/util/Map;->containsKey(Ljava/lang/Object;)Z

    move-result v18

    if-nez v18, :cond_ae

    invoke-interface {v3, v13}, Ljava/util/Map;->containsKey(Ljava/lang/Object;)Z

    move-result v18

    if-nez v18, :cond_ae

    invoke-interface {v3, v11}, Ljava/util/Map;->containsKey(Ljava/lang/Object;)Z

    move-result v18

    if-nez v18, :cond_ae

    invoke-interface {v3, v10}, Ljava/util/Map;->containsKey(Ljava/lang/Object;)Z

    move-result v18

    if-nez v18, :cond_ae

    invoke-interface {v3, v8}, Ljava/util/Map;->containsKey(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_d

    goto :goto_ae

    .line 1194
    :cond_5e
    move-object/from16 v2, p1

    check-cast v2, Ljava/util/Collection;

    invoke-interface {v2}, Ljava/util/Collection;->isEmpty()Z

    move-result v2

    if-nez v2, :cond_ae

    .line 1195
    move-object/from16 v1, p1

    check-cast v1, Ljava/lang/Iterable;

    .line 1908
    new-instance v2, Ljava/util/ArrayList;

    invoke-direct {v2}, Ljava/util/ArrayList;-><init>()V

    check-cast v2, Ljava/util/Collection;

    .line 1917
    invoke-interface {v1}, Ljava/lang/Iterable;->iterator()Ljava/util/Iterator;

    move-result-object v1

    :cond_77
    :goto_77
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_8d

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    .line 1916
    check-cast v3, Ljava/util/Map;

    .line 1195
    invoke-direct {v0, v3}, Lcom/example/data/network/LocalMovieBoxServer;->mapItem(Ljava/util/Map;)Ljava/util/Map;

    move-result-object v3

    if-eqz v3, :cond_77

    .line 1916
    invoke-interface {v2, v3}, Ljava/util/Collection;->add(Ljava/lang/Object;)Z

    goto :goto_77

    .line 1920
    :cond_8d
    check-cast v2, Ljava/util/List;

    .line 1197
    new-array v0, v7, [Lkotlin/Pair;

    const-string v1, "Movies"

    invoke-static {v14, v1}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v1

    aput-object v1, v0, v17

    .line 1198
    invoke-static {v9, v5}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v1

    aput-object v1, v0, v12

    .line 1199
    invoke-static {v6, v2}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v1

    aput-object v1, v0, v16

    .line 1196
    invoke-static {v0}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v0

    invoke-static {v0}, Lkotlin/collections/CollectionsKt;->listOf(Ljava/lang/Object;)Ljava/util/List;

    move-result-object v0

    return-object v0

    .line 1203
    :cond_ae
    :goto_ae
    invoke-interface/range {p1 .. p1}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :goto_b2
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_2cc

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/util/Map;

    .line 1204
    invoke-interface {v3, v14}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v18

    if-nez v18, :cond_ce

    const-string v7, "name"

    invoke-interface {v3, v7}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v18

    if-nez v18, :cond_ce

    const-string v18, "Section"

    :cond_ce
    move-object/from16 v7, v18

    .line 1205
    invoke-virtual {v7}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v18

    move-object/from16 v12, v18

    check-cast v12, Ljava/lang/CharSequence;

    const-string v18, "Join Us"

    move-object/from16 p1, v2

    move-object/from16 v2, v18

    check-cast v2, Ljava/lang/CharSequence;

    move-object/from16 v18, v5

    const/4 v5, 0x1

    invoke-static {v12, v2, v5}, Lkotlin/text/StringsKt;->contains(Ljava/lang/CharSequence;Ljava/lang/CharSequence;Z)Z

    move-result v2

    if-nez v2, :cond_2c1

    .line 1207
    new-instance v2, Ljava/util/ArrayList;

    invoke-direct {v2}, Ljava/util/ArrayList;-><init>()V

    check-cast v2, Ljava/util/List;

    .line 1209
    invoke-interface {v3, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    instance-of v12, v5, Ljava/util/Map;

    const/16 v20, 0x0

    if-eqz v12, :cond_fd

    check-cast v5, Ljava/util/Map;

    goto :goto_ff

    :cond_fd
    move-object/from16 v5, v20

    :goto_ff
    if-eqz v5, :cond_106

    invoke-interface {v5, v6}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    goto :goto_108

    :cond_106
    move-object/from16 v5, v20

    :goto_108
    instance-of v12, v5, Ljava/util/List;

    move-object/from16 v21, v5

    const-string v5, "null cannot be cast to non-null type kotlin.collections.Map<kotlin.String, kotlin.Any?>"

    move/from16 v22, v12

    const-string v12, "null cannot be cast to non-null type kotlin.collections.List<kotlin.collections.Map<kotlin.String, kotlin.Any?>>"

    if-eqz v22, :cond_136

    check-cast v21, Ljava/util/Collection;

    invoke-interface/range {v21 .. v21}, Ljava/util/Collection;->isEmpty()Z

    move-result v21

    if-nez v21, :cond_136

    move-object/from16 v21, v1

    .line 1210
    invoke-interface {v3, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    invoke-static {v1, v5}, Lkotlin/jvm/internal/Intrinsics;->checkNotNull(Ljava/lang/Object;Ljava/lang/String;)V

    check-cast v1, Ljava/util/Map;

    .line 1211
    invoke-interface {v1, v6}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    invoke-static {v1, v12}, Lkotlin/jvm/internal/Intrinsics;->checkNotNull(Ljava/lang/Object;Ljava/lang/String;)V

    check-cast v1, Ljava/util/List;

    move-object/from16 v23, v9

    move-object/from16 v22, v11

    goto/16 :goto_20d

    :cond_136
    move-object/from16 v21, v1

    .line 1213
    invoke-interface {v3, v10}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    move-object/from16 v22, v11

    instance-of v11, v1, Ljava/util/Map;

    if-eqz v11, :cond_145

    check-cast v1, Ljava/util/Map;

    goto :goto_147

    :cond_145
    move-object/from16 v1, v20

    :goto_147
    const-string v11, "banners"

    if-eqz v1, :cond_150

    invoke-interface {v1, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    goto :goto_152

    :cond_150
    move-object/from16 v1, v20

    :goto_152
    move-object/from16 v23, v9

    instance-of v9, v1, Ljava/util/List;

    if-eqz v9, :cond_174

    check-cast v1, Ljava/util/Collection;

    invoke-interface {v1}, Ljava/util/Collection;->isEmpty()Z

    move-result v1

    if-nez v1, :cond_174

    .line 1214
    invoke-interface {v3, v10}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    invoke-static {v1, v5}, Lkotlin/jvm/internal/Intrinsics;->checkNotNull(Ljava/lang/Object;Ljava/lang/String;)V

    check-cast v1, Ljava/util/Map;

    .line 1215
    invoke-interface {v1, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    invoke-static {v1, v12}, Lkotlin/jvm/internal/Intrinsics;->checkNotNull(Ljava/lang/Object;Ljava/lang/String;)V

    check-cast v1, Ljava/util/List;

    goto/16 :goto_20d

    .line 1217
    :cond_174
    invoke-interface {v3, v8}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    instance-of v9, v1, Ljava/util/Map;

    if-eqz v9, :cond_1a1

    check-cast v1, Ljava/util/Map;

    invoke-interface {v1}, Ljava/util/Map;->isEmpty()Z

    move-result v1

    if-nez v1, :cond_1a1

    .line 1218
    invoke-interface {v3, v8}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    invoke-static {v1, v5}, Lkotlin/jvm/internal/Intrinsics;->checkNotNull(Ljava/lang/Object;Ljava/lang/String;)V

    check-cast v1, Ljava/util/Map;

    .line 1219
    invoke-interface {v1, v6}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    instance-of v5, v1, Ljava/util/List;

    if-eqz v5, :cond_198

    check-cast v1, Ljava/util/List;

    goto :goto_19a

    :cond_198
    move-object/from16 v1, v20

    :goto_19a
    if-nez v1, :cond_20d

    invoke-static {}, Lkotlin/collections/CollectionsKt;->emptyList()Ljava/util/List;

    move-result-object v1

    goto :goto_20d

    .line 1221
    :cond_1a1
    invoke-interface {v3, v4}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    instance-of v5, v1, Ljava/util/List;

    if-eqz v5, :cond_1bb

    check-cast v1, Ljava/util/Collection;

    invoke-interface {v1}, Ljava/util/Collection;->isEmpty()Z

    move-result v1

    if-nez v1, :cond_1bb

    .line 1222
    invoke-interface {v3, v4}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    invoke-static {v1, v12}, Lkotlin/jvm/internal/Intrinsics;->checkNotNull(Ljava/lang/Object;Ljava/lang/String;)V

    check-cast v1, Ljava/util/List;

    goto :goto_20d

    .line 1224
    :cond_1bb
    invoke-interface {v3, v6}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    instance-of v5, v1, Ljava/util/List;

    if-eqz v5, :cond_1d5

    check-cast v1, Ljava/util/Collection;

    invoke-interface {v1}, Ljava/util/Collection;->isEmpty()Z

    move-result v1

    if-nez v1, :cond_1d5

    .line 1225
    invoke-interface {v3, v6}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    invoke-static {v1, v12}, Lkotlin/jvm/internal/Intrinsics;->checkNotNull(Ljava/lang/Object;Ljava/lang/String;)V

    check-cast v1, Ljava/util/List;

    goto :goto_20d

    .line 1227
    :cond_1d5
    invoke-interface {v3, v15}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    instance-of v5, v1, Ljava/util/List;

    if-eqz v5, :cond_1ef

    check-cast v1, Ljava/util/Collection;

    invoke-interface {v1}, Ljava/util/Collection;->isEmpty()Z

    move-result v1

    if-nez v1, :cond_1ef

    .line 1228
    invoke-interface {v3, v15}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    invoke-static {v1, v12}, Lkotlin/jvm/internal/Intrinsics;->checkNotNull(Ljava/lang/Object;Ljava/lang/String;)V

    check-cast v1, Ljava/util/List;

    goto :goto_20d

    .line 1230
    :cond_1ef
    invoke-interface {v3, v13}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    instance-of v5, v1, Ljava/util/List;

    if-eqz v5, :cond_209

    check-cast v1, Ljava/util/Collection;

    invoke-interface {v1}, Ljava/util/Collection;->isEmpty()Z

    move-result v1

    if-nez v1, :cond_209

    .line 1231
    invoke-interface {v3, v13}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    invoke-static {v1, v12}, Lkotlin/jvm/internal/Intrinsics;->checkNotNull(Ljava/lang/Object;Ljava/lang/String;)V

    check-cast v1, Ljava/util/List;

    goto :goto_20d

    .line 1233
    :cond_209
    invoke-static {}, Lkotlin/collections/CollectionsKt;->emptyList()Ljava/util/List;

    move-result-object v1

    .line 1236
    :cond_20d
    :goto_20d
    invoke-interface {v1}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v1

    :cond_211
    :goto_211
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v5

    if-eqz v5, :cond_246

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Ljava/util/Map;

    .line 1237
    const-string v9, "subject"

    invoke-interface {v5, v9}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v9

    instance-of v11, v9, Ljava/util/Map;

    if-eqz v11, :cond_22a

    check-cast v9, Ljava/util/Map;

    goto :goto_22c

    :cond_22a
    move-object/from16 v9, v20

    :goto_22c
    if-eqz v9, :cond_232

    .line 1239
    invoke-interface {v2, v9}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    goto :goto_211

    .line 1240
    :cond_232
    const-string v9, "subjectId"

    invoke-interface {v5, v9}, Ljava/util/Map;->containsKey(Ljava/lang/Object;)Z

    move-result v9

    if-nez v9, :cond_242

    const-string v9, "id"

    invoke-interface {v5, v9}, Ljava/util/Map;->containsKey(Ljava/lang/Object;)Z

    move-result v9

    if-eqz v9, :cond_211

    .line 1241
    :cond_242
    invoke-interface {v2, v5}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    goto :goto_211

    .line 1245
    :cond_246
    move-object v1, v2

    check-cast v1, Ljava/util/Collection;

    invoke-interface {v1}, Ljava/util/Collection;->isEmpty()Z

    move-result v1

    if-nez v1, :cond_2b2

    .line 1246
    check-cast v2, Ljava/lang/Iterable;

    .line 1922
    new-instance v1, Ljava/util/ArrayList;

    invoke-direct {v1}, Ljava/util/ArrayList;-><init>()V

    check-cast v1, Ljava/util/Collection;

    .line 1931
    invoke-interface {v2}, Ljava/lang/Iterable;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :cond_25c
    :goto_25c
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v5

    if-eqz v5, :cond_272

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v5

    .line 1930
    check-cast v5, Ljava/util/Map;

    .line 1246
    invoke-direct {v0, v5}, Lcom/example/data/network/LocalMovieBoxServer;->mapItem(Ljava/util/Map;)Ljava/util/Map;

    move-result-object v5

    if-eqz v5, :cond_25c

    .line 1930
    invoke-interface {v1, v5}, Ljava/util/Collection;->add(Ljava/lang/Object;)Z

    goto :goto_25c

    .line 1934
    :cond_272
    check-cast v1, Ljava/util/List;

    const/4 v2, 0x3

    .line 1248
    new-array v5, v2, [Lkotlin/Pair;

    invoke-static {v14, v7}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v7

    aput-object v7, v5, v17

    .line 1249
    const-string v7, "subjectType"

    invoke-interface {v3, v7}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v7

    move-object/from16 v9, v23

    if-nez v7, :cond_28f

    invoke-interface {v3, v9}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v7

    if-nez v7, :cond_28f

    move-object/from16 v7, v18

    :cond_28f
    invoke-static {v9, v7}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v3

    const/16 v19, 0x1

    aput-object v3, v5, v19

    .line 1250
    invoke-static {v6, v1}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v1

    aput-object v1, v5, v16

    .line 1247
    invoke-static {v5}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v1

    move-object/from16 v3, v21

    invoke-interface {v3, v1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    move v7, v2

    move-object v1, v3

    move-object/from16 v5, v18

    move/from16 v12, v19

    move-object/from16 v11, v22

    move-object/from16 v2, p1

    goto/16 :goto_b2

    :cond_2b2
    const/16 v19, 0x1

    move-object/from16 v2, p1

    move-object/from16 v5, v18

    move/from16 v12, v19

    move-object/from16 v1, v21

    move-object/from16 v11, v22

    move-object/from16 v9, v23

    goto :goto_2c9

    :cond_2c1
    const/16 v19, 0x1

    move-object/from16 v2, p1

    move-object/from16 v5, v18

    move/from16 v12, v19

    :goto_2c9
    const/4 v7, 0x3

    goto/16 :goto_b2

    :cond_2cc
    move-object v3, v1

    return-object v3
.end method

.method private final getDeviceId()Ljava/lang/String;
    .registers 9

    .line 1610
    iget-object p0, p0, Lcom/example/data/network/LocalMovieBoxServer;->context:Landroid/content/Context;

    const-string v0, "moviebox_prefs"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Landroid/content/Context;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object p0

    const/4 v0, 0x0

    .line 1611
    const-string v1, "device_id"

    invoke-interface {p0, v1, v0}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 1612
    move-object v2, v0

    check-cast v2, Ljava/lang/CharSequence;

    if-eqz v2, :cond_1d

    invoke-interface {v2}, Ljava/lang/CharSequence;->length()I

    move-result v2

    if-nez v2, :cond_1c

    goto :goto_1d

    :cond_1c
    return-object v0

    .line 1613
    :cond_1d
    :goto_1d
    invoke-static {}, Ljava/util/UUID;->randomUUID()Ljava/util/UUID;

    move-result-object v0

    invoke-virtual {v0}, Ljava/util/UUID;->toString()Ljava/lang/String;

    move-result-object v2

    const-string v0, "toString(...)"

    invoke-static {v2, v0}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V

    const/4 v6, 0x4

    const/4 v7, 0x0

    const-string v3, "-"

    const-string v4, ""

    const/4 v5, 0x0

    invoke-static/range {v2 .. v7}, Lkotlin/text/StringsKt;->replace$default(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    .line 1614
    invoke-interface {p0}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object p0

    invoke-interface {p0, v1, v0}, Landroid/content/SharedPreferences$Editor;->putString(Ljava/lang/String;Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;

    move-result-object p0

    invoke-interface {p0}, Landroid/content/SharedPreferences$Editor;->apply()V

    return-object v0
.end method

.method private final getMockTrending()Ljava/util/List;
    .registers 25
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/List<",
            "Ljava/util/Map<",
            "Ljava/lang/String;",
            "Ljava/lang/Object;",
            ">;>;"
        }
    .end annotation

    const/4 v0, 0x5

    .line 1640
    new-array v1, v0, [Ljava/util/Map;

    const/16 v2, 0xa

    .line 1641
    new-array v3, v2, [Lkotlin/Pair;

    const-string v4, "subjectId"

    const-string v5, "11059"

    invoke-static {v4, v5}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v6

    const/4 v7, 0x0

    aput-object v6, v3, v7

    .line 1642
    const-string v6, "id"

    invoke-static {v6, v5}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v5

    const/4 v8, 0x1

    .line 1648
    invoke-static {v8}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v9

    .line 1642
    aput-object v5, v3, v8

    .line 1643
    const-string v5, "The Matrix Resurrections"

    const-string v10, "title"

    invoke-static {v10, v5}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v5

    const/4 v11, 0x2

    aput-object v5, v3, v11

    .line 1644
    const-string v5, "https://image.tmdb.org/t/p/w500/8cZ1777vX6Y1RLuSgEpY66i9d6y.jpg"

    const-string v12, "cover"

    invoke-static {v12, v5}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v5

    const/4 v13, 0x3

    aput-object v5, v3, v13

    .line 1645
    const-string v5, "https://image.tmdb.org/t/p/w500/8cZ1777vX6Y1RLuSgEpY66i9d6y.jpg"

    const-string v14, "poster"

    invoke-static {v14, v5}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v5

    const/4 v15, 0x4

    aput-object v5, v3, v15

    .line 1646
    const-string v5, "7.2"

    move/from16 p0, v0

    const-string v0, "score"

    invoke-static {v0, v5}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v5

    aput-object v5, v3, p0

    .line 1647
    const-string v5, "2021"

    move/from16 v16, v7

    const-string v7, "releaseTime"

    invoke-static {v7, v5}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v5

    const/16 v17, 0x6

    aput-object v5, v3, v17

    .line 1648
    const-string v5, "subjectType"

    invoke-static {v5, v9}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v18

    const/16 v19, 0x7

    aput-object v18, v3, v19

    move/from16 v18, v8

    .line 1649
    const-string v8, "148 mins"

    move/from16 v20, v11

    const-string v11, "runtime"

    invoke-static {v11, v8}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v8

    const/16 v21, 0x8

    aput-object v8, v3, v21

    .line 1650
    const-string v8, "Return to a world of two realities: one, everyday life; the other, what lies behind it. To find out if his reality is a physical or mental construct, to truly know himself, Mr. Anderson will have to choose to follow the white rabbit once more."

    move/from16 v22, v13

    const-string v13, "description"

    invoke-static {v13, v8}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v8

    const/16 v23, 0x9

    aput-object v8, v3, v23

    .line 1640
    invoke-static {v3}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v3

    aput-object v3, v1, v16

    .line 1653
    new-array v3, v2, [Lkotlin/Pair;

    const-string v8, "10543"

    invoke-static {v4, v8}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v8

    aput-object v8, v3, v16

    .line 1654
    const-string v8, "10543"

    invoke-static {v6, v8}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v8

    aput-object v8, v3, v18

    .line 1655
    const-string v8, "Spider-Man: No Way Home"

    invoke-static {v10, v8}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v8

    aput-object v8, v3, v20

    .line 1656
    const-string v8, "https://image.tmdb.org/t/p/w500/1g0dhvJI6H9Ygjr6YvA1vGZ6P6V.jpg"

    invoke-static {v12, v8}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v8

    aput-object v8, v3, v22

    .line 1657
    const-string v8, "https://image.tmdb.org/t/p/w500/1g0dhvJI6H9Ygjr6YvA1vGZ6P6V.jpg"

    invoke-static {v14, v8}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v8

    aput-object v8, v3, v15

    .line 1658
    const-string v8, "8.7"

    invoke-static {v0, v8}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v8

    aput-object v8, v3, p0

    .line 1659
    const-string v8, "2021"

    invoke-static {v7, v8}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v8

    aput-object v8, v3, v17

    .line 1660
    invoke-static {v5, v9}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v8

    aput-object v8, v3, v19

    .line 1661
    const-string v8, "148 mins"

    invoke-static {v11, v8}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v8

    aput-object v8, v3, v21

    .line 1662
    const-string v8, "Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero. When he asks for help from Doctor Strange the stakes become even more dangerous, forcing him to discover what it truly means to be Spider-Man."

    invoke-static {v13, v8}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v8

    aput-object v8, v3, v23

    .line 1652
    invoke-static {v3}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v3

    aput-object v3, v1, v18

    .line 1665
    new-array v3, v2, [Lkotlin/Pair;

    const-string v8, "11382"

    invoke-static {v4, v8}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v8

    aput-object v8, v3, v16

    .line 1666
    const-string v8, "11382"

    invoke-static {v6, v8}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v8

    aput-object v8, v3, v18

    .line 1667
    const-string v8, "Dune: Part Two"

    invoke-static {v10, v8}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v8

    aput-object v8, v3, v20

    .line 1668
    const-string v8, "https://image.tmdb.org/t/p/w500/cz062Sg88Vv6RAsmY8g8b0GgX6n.jpg"

    invoke-static {v12, v8}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v8

    aput-object v8, v3, v22

    .line 1669
    const-string v8, "https://image.tmdb.org/t/p/w500/cz062Sg88Vv6RAsmY8g8b0GgX6n.jpg"

    invoke-static {v14, v8}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v8

    aput-object v8, v3, v15

    .line 1670
    const-string v8, "8.9"

    invoke-static {v0, v8}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v8

    aput-object v8, v3, p0

    .line 1671
    const-string v8, "2024"

    invoke-static {v7, v8}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v8

    aput-object v8, v3, v17

    .line 1672
    invoke-static {v5, v9}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v8

    aput-object v8, v3, v19

    .line 1673
    const-string v8, "166 mins"

    invoke-static {v11, v8}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v8

    aput-object v8, v3, v21

    .line 1674
    const-string v8, "Follow the mythic journey of Paul Atreides as he unites with Chani and the Fremen while on a path of revenge against the conspirators who destroyed his family."

    invoke-static {v13, v8}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v8

    aput-object v8, v3, v23

    .line 1664
    invoke-static {v3}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v3

    aput-object v3, v1, v20

    .line 1677
    new-array v3, v2, [Lkotlin/Pair;

    const-string v8, "11500"

    invoke-static {v4, v8}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v8

    aput-object v8, v3, v16

    .line 1678
    const-string v8, "11500"

    invoke-static {v6, v8}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v8

    aput-object v8, v3, v18

    .line 1679
    const-string v8, "Oppenheimer"

    invoke-static {v10, v8}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v8

    aput-object v8, v3, v20

    .line 1680
    const-string v8, "https://image.tmdb.org/t/p/w500/8Gxv2gSjdhY7WbgSg1f07st662B.jpg"

    invoke-static {v12, v8}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v8

    aput-object v8, v3, v22

    .line 1681
    const-string v8, "https://image.tmdb.org/t/p/w500/8Gxv2gSjdhY7WbgSg1f07st662B.jpg"

    invoke-static {v14, v8}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v8

    aput-object v8, v3, v15

    .line 1682
    const-string v8, "8.6"

    invoke-static {v0, v8}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v8

    aput-object v8, v3, p0

    .line 1683
    const-string v8, "2023"

    invoke-static {v7, v8}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v8

    aput-object v8, v3, v17

    .line 1684
    invoke-static {v5, v9}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v8

    aput-object v8, v3, v19

    .line 1685
    const-string v8, "180 mins"

    invoke-static {v11, v8}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v8

    aput-object v8, v3, v21

    .line 1686
    const-string v8, "The story of American scientist J. Robert Oppenheimer and his role in the development of the atomic bomb."

    invoke-static {v13, v8}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v8

    aput-object v8, v3, v23

    .line 1676
    invoke-static {v3}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v3

    aput-object v3, v1, v22

    .line 1689
    new-array v2, v2, [Lkotlin/Pair;

    const-string v3, "11650"

    invoke-static {v4, v3}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v3

    aput-object v3, v2, v16

    .line 1690
    const-string v3, "11650"

    invoke-static {v6, v3}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v3

    aput-object v3, v2, v18

    .line 1691
    const-string v3, "Interstellar"

    invoke-static {v10, v3}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v3

    aput-object v3, v2, v20

    .line 1692
    const-string v3, "https://image.tmdb.org/t/p/w500/gEU2Qv6Xg77DJ9v3vG6mI80m2fN.jpg"

    invoke-static {v12, v3}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v3

    aput-object v3, v2, v22

    .line 1693
    const-string v3, "https://image.tmdb.org/t/p/w500/gEU2Qv6Xg77DJ9v3vG6mI80m2fN.jpg"

    invoke-static {v14, v3}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v3

    aput-object v3, v2, v15

    .line 1694
    const-string v3, "8.7"

    invoke-static {v0, v3}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    aput-object v0, v2, p0

    .line 1695
    const-string v0, "2014"

    invoke-static {v7, v0}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    aput-object v0, v2, v17

    .line 1696
    invoke-static {v5, v9}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    aput-object v0, v2, v19

    .line 1697
    const-string v0, "169 mins"

    invoke-static {v11, v0}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    aput-object v0, v2, v21

    .line 1698
    const-string v0, "The adventures of a group of explorers who make use of a newly discovered wormhole to surpass the limitations on human space travel and conquer the vast distances involved in an interstellar voyage."

    invoke-static {v13, v0}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    aput-object v0, v2, v23

    .line 1688
    invoke-static {v2}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v0

    aput-object v0, v1, v15

    .line 1639
    invoke-static {v1}, Lkotlin/collections/CollectionsKt;->listOf([Ljava/lang/Object;)Ljava/util/List;

    move-result-object v0

    return-object v0
.end method

.method private final handleClient(Ljava/net/Socket;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;
    .registers 6
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/net/Socket;",
            "Lkotlin/coroutines/Continuation<",
            "-",
            "Lkotlin/Unit;",
            ">;)",
            "Ljava/lang/Object;"
        }
    .end annotation

    .line 82
    invoke-static {}, Lkotlinx/coroutines/Dispatchers;->getIO()Lkotlinx/coroutines/CoroutineDispatcher;

    move-result-object v0

    check-cast v0, Lkotlin/coroutines/CoroutineContext;

    new-instance v1, Lcom/example/data/network/LocalMovieBoxServer$handleClient$2;

    const/4 v2, 0x0

    invoke-direct {v1, p1, p0, v2}, Lcom/example/data/network/LocalMovieBoxServer$handleClient$2;-><init>(Ljava/net/Socket;Lcom/example/data/network/LocalMovieBoxServer;Lkotlin/coroutines/Continuation;)V

    check-cast v1, Lkotlin/jvm/functions/Function2;

    invoke-static {v0, v1, p2}, Lkotlinx/coroutines/BuildersKt;->withContext(Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;

    move-result-object p0

    invoke-static {}, Lkotlin/coroutines/intrinsics/IntrinsicsKt;->getCOROUTINE_SUSPENDED()Ljava/lang/Object;

    move-result-object p1

    if-ne p0, p1, :cond_19

    return-object p0

    :cond_19
    sget-object p0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;

    return-object p0
.end method

.method private final hmacMd5([B[B)[B
    .registers 5

    .line 1632
    const-string p0, "HmacMD5"

    invoke-static {p0}, Ljavax/crypto/Mac;->getInstance(Ljava/lang/String;)Ljavax/crypto/Mac;

    move-result-object v0

    .line 1633
    new-instance v1, Ljavax/crypto/spec/SecretKeySpec;

    invoke-direct {v1, p1, p0}, Ljavax/crypto/spec/SecretKeySpec;-><init>([BLjava/lang/String;)V

    .line 1634
    check-cast v1, Ljava/security/Key;

    invoke-virtual {v0, v1}, Ljavax/crypto/Mac;->init(Ljava/security/Key;)V

    .line 1635
    invoke-virtual {v0, p2}, Ljavax/crypto/Mac;->doFinal([B)[B

    move-result-object p0

    const-string p1, "doFinal(...)"

    invoke-static {p0, p1}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V

    return-object p0
.end method

.method private final jsonToList(Lorg/json/JSONArray;)Ljava/util/List;
    .registers 7
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lorg/json/JSONArray;",
            ")",
            "Ljava/util/List<",
            "Ljava/lang/Object;",
            ">;"
        }
    .end annotation

    .line 1775
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    check-cast v0, Ljava/util/List;

    .line 1776
    invoke-virtual {p1}, Lorg/json/JSONArray;->length()I

    move-result v1

    const/4 v2, 0x0

    :goto_c
    if-ge v2, v1, :cond_37

    .line 1777
    invoke-virtual {p1, v2}, Lorg/json/JSONArray;->get(I)Ljava/lang/Object;

    move-result-object v3

    .line 1779
    instance-of v4, v3, Lorg/json/JSONObject;

    if-eqz v4, :cond_1d

    check-cast v3, Lorg/json/JSONObject;

    invoke-direct {p0, v3}, Lcom/example/data/network/LocalMovieBoxServer;->jsonToMap(Lorg/json/JSONObject;)Ljava/util/Map;

    move-result-object v3

    goto :goto_31

    .line 1780
    :cond_1d
    instance-of v4, v3, Lorg/json/JSONArray;

    if-eqz v4, :cond_28

    check-cast v3, Lorg/json/JSONArray;

    invoke-direct {p0, v3}, Lcom/example/data/network/LocalMovieBoxServer;->jsonToList(Lorg/json/JSONArray;)Ljava/util/List;

    move-result-object v3

    goto :goto_31

    .line 1781
    :cond_28
    sget-object v4, Lorg/json/JSONObject;->NULL:Ljava/lang/Object;

    invoke-static {v3, v4}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_31

    const/4 v3, 0x0

    .line 1778
    :cond_31
    :goto_31
    invoke-interface {v0, v3}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    add-int/lit8 v2, v2, 0x1

    goto :goto_c

    :cond_37
    return-object v0
.end method

.method private final jsonToMap(Lorg/json/JSONObject;)Ljava/util/Map;
    .registers 7
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lorg/json/JSONObject;",
            ")",
            "Ljava/util/Map<",
            "Ljava/lang/String;",
            "Ljava/lang/Object;",
            ">;"
        }
    .end annotation

    .line 1759
    new-instance v0, Ljava/util/LinkedHashMap;

    invoke-direct {v0}, Ljava/util/LinkedHashMap;-><init>()V

    check-cast v0, Ljava/util/Map;

    .line 1760
    invoke-virtual {p1}, Lorg/json/JSONObject;->keys()Ljava/util/Iterator;

    move-result-object v1

    .line 1761
    :goto_b
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    if-eqz v2, :cond_3e

    .line 1762
    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/String;

    .line 1763
    invoke-virtual {p1, v2}, Lorg/json/JSONObject;->get(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v3

    .line 1765
    instance-of v4, v3, Lorg/json/JSONObject;

    if-eqz v4, :cond_26

    check-cast v3, Lorg/json/JSONObject;

    invoke-direct {p0, v3}, Lcom/example/data/network/LocalMovieBoxServer;->jsonToMap(Lorg/json/JSONObject;)Ljava/util/Map;

    move-result-object v3

    goto :goto_3a

    .line 1766
    :cond_26
    instance-of v4, v3, Lorg/json/JSONArray;

    if-eqz v4, :cond_31

    check-cast v3, Lorg/json/JSONArray;

    invoke-direct {p0, v3}, Lcom/example/data/network/LocalMovieBoxServer;->jsonToList(Lorg/json/JSONArray;)Ljava/util/List;

    move-result-object v3

    goto :goto_3a

    .line 1767
    :cond_31
    sget-object v4, Lorg/json/JSONObject;->NULL:Ljava/lang/Object;

    invoke-static {v3, v4}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_3a

    const/4 v3, 0x0

    .line 1764
    :cond_3a
    :goto_3a
    invoke-interface {v0, v2, v3}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    goto :goto_b

    :cond_3e
    return-object v0
.end method

.method private final makeOfficialRequest(Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;)Ljava/lang/String;
    .registers 44
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/String;",
            "Ljava/lang/String;",
            "Ljava/util/Map<",
            "Ljava/lang/String;",
            "Ljava/lang/String;",
            ">;",
            "Ljava/lang/String;",
            ")",
            "Ljava/lang/String;"
        }
    .end annotation

    move-object/from16 v1, p0

    move-object/from16 v2, p1

    move-object/from16 v0, p2

    move-object/from16 v3, p4

    .line 1342
    const-string v4, "LocalMovieBoxServer"

    iget-object v5, v1, Lcom/example/data/network/LocalMovieBoxServer;->context:Landroid/content/Context;

    const-string v6, "moviebox_prefs"

    const/4 v7, 0x0

    invoke-virtual {v5, v6, v7}, Landroid/content/Context;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v5

    .line 1343
    const-string v6, "sp_code"

    const-string v8, ""

    invoke-interface {v5, v6, v8}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v9

    if-eqz v9, :cond_2a

    move-object v11, v9

    check-cast v11, Ljava/lang/CharSequence;

    invoke-interface {v11}, Ljava/lang/CharSequence;->length()I

    move-result v11

    if-lez v11, :cond_27

    goto :goto_28

    :cond_27
    const/4 v9, 0x0

    :goto_28
    if-nez v9, :cond_2c

    :cond_2a
    const-string v9, "404"

    .line 1344
    :cond_2c
    const-string v11, "custom_local_iso"

    invoke-interface {v5, v11, v8}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v11

    const-string v12, "IN"

    if-eqz v11, :cond_50

    move-object v13, v11

    check-cast v13, Ljava/lang/CharSequence;

    invoke-interface {v13}, Ljava/lang/CharSequence;->length()I

    move-result v13

    if-lez v13, :cond_40

    goto :goto_41

    :cond_40
    const/4 v11, 0x0

    :goto_41
    if-eqz v11, :cond_50

    sget-object v13, Ljava/util/Locale;->ROOT:Ljava/util/Locale;

    invoke-virtual {v11, v13}, Ljava/lang/String;->toUpperCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object v11

    const-string v13, "toUpperCase(...)"

    invoke-static {v11, v13}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V

    if-nez v11, :cond_51

    :cond_50
    move-object v11, v12

    .line 1345
    :cond_51
    invoke-virtual {v11}, Ljava/lang/String;->hashCode()I

    move-result v13

    const-string v14, "US"

    const/16 v7, 0xa43

    const/16 v10, 0x850

    const-string v15, "RU"

    move-object/from16 v17, v5

    const-string v5, "BR"

    if-eq v13, v10, :cond_7d

    if-eq v13, v7, :cond_73

    const/16 v7, 0xa9e

    if-eq v13, v7, :cond_6a

    goto :goto_83

    :cond_6a
    invoke-virtual {v11, v14}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v7

    if-eqz v7, :cond_83

    const-string v7, "America/New_York"

    goto :goto_88

    :cond_73
    invoke-virtual {v11, v15}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v7

    if-nez v7, :cond_7a

    goto :goto_83

    :cond_7a
    const-string v7, "Europe/Moscow"

    goto :goto_88

    :cond_7d
    invoke-virtual {v11, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v7

    if-nez v7, :cond_86

    :cond_83
    :goto_83
    const-string v7, "Asia/Kolkata"

    goto :goto_88

    :cond_86
    const-string v7, "America/Sao_Paulo"

    .line 1347
    :goto_88
    const-string v13, "GET"

    invoke-static {v2, v13}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v13

    const/16 v18, 0x1

    const/4 v10, 0x2

    if-eqz v13, :cond_b5

    move-object v13, v0

    check-cast v13, Ljava/lang/CharSequence;

    const-string v19, "profile"

    move-object/from16 v20, v5

    move-object/from16 v5, v19

    check-cast v5, Ljava/lang/CharSequence;

    move-object/from16 v19, v12

    move-object/from16 v21, v15

    const/4 v12, 0x0

    const/4 v15, 0x0

    invoke-static {v13, v5, v15, v10, v12}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v5

    if-nez v5, :cond_bd

    const-string v5, "see-list-v2"

    check-cast v5, Ljava/lang/CharSequence;

    invoke-static {v13, v5, v15, v10, v12}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_113

    goto :goto_bd

    :cond_b5
    move-object/from16 v20, v5

    move-object/from16 v19, v12

    move-object/from16 v21, v15

    const/4 v12, 0x0

    const/4 v15, 0x0

    :cond_bd
    :goto_bd
    move-object v5, v0

    check-cast v5, Ljava/lang/CharSequence;

    const-string v13, "/subject-api/get"

    check-cast v13, Ljava/lang/CharSequence;

    invoke-static {v5, v13, v15, v10, v12}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v13

    if-nez v13, :cond_113

    const-string v13, "/subject-api/trending"

    check-cast v13, Ljava/lang/CharSequence;

    invoke-static {v5, v13, v15, v10, v12}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v13

    if-nez v13, :cond_113

    const-string v13, "/subject-api/top-rec"

    check-cast v13, Ljava/lang/CharSequence;

    invoke-static {v5, v13, v15, v10, v12}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v13

    if-nez v13, :cond_113

    const-string v13, "/subject-api/search"

    check-cast v13, Ljava/lang/CharSequence;

    invoke-static {v5, v13, v15, v10, v12}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v13

    if-nez v13, :cond_113

    const-string v13, "/subject-api/search-rank"

    check-cast v13, Ljava/lang/CharSequence;

    invoke-static {v5, v13, v15, v10, v12}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v13

    if-nez v13, :cond_113

    const-string v13, "/subject-api/season-info"

    check-cast v13, Ljava/lang/CharSequence;

    invoke-static {v5, v13, v15, v10, v12}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v13

    if-nez v13, :cond_113

    const-string v13, "/subject-api/play-info"

    check-cast v13, Ljava/lang/CharSequence;

    invoke-static {v5, v13, v15, v10, v12}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v13

    if-nez v13, :cond_113

    const-string v13, "/subject-api/get-ext-captions"

    check-cast v13, Ljava/lang/CharSequence;

    invoke-static {v5, v13, v15, v10, v12}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_111

    goto :goto_113

    :cond_111
    const/4 v15, 0x0

    goto :goto_115

    :cond_113
    :goto_113
    move/from16 v15, v18

    .line 1348
    :goto_115
    invoke-interface/range {p3 .. p3}, Ljava/util/Map;->entrySet()Ljava/util/Set;

    move-result-object v5

    check-cast v5, Ljava/lang/Iterable;

    .line 1936
    new-instance v12, Lcom/example/data/network/LocalMovieBoxServer$makeOfficialRequest$$inlined$sortedBy$1;

    invoke-direct {v12}, Lcom/example/data/network/LocalMovieBoxServer$makeOfficialRequest$$inlined$sortedBy$1;-><init>()V

    check-cast v12, Ljava/util/Comparator;

    invoke-static {v5, v12}, Lkotlin/collections/CollectionsKt;->sortedWith(Ljava/lang/Iterable;Ljava/util/Comparator;)Ljava/util/List;

    move-result-object v5

    move-object/from16 v22, v5

    check-cast v22, Ljava/lang/Iterable;

    .line 1348
    const-string v5, "&"

    move-object/from16 v23, v5

    check-cast v23, Ljava/lang/CharSequence;

    new-instance v28, Lcom/example/data/network/LocalMovieBoxServer$$ExternalSyntheticLambda0;

    invoke-direct/range {v28 .. v28}, Lcom/example/data/network/LocalMovieBoxServer$$ExternalSyntheticLambda0;-><init>()V

    const/16 v29, 0x1e

    const/16 v30, 0x0

    const/16 v24, 0x0

    const/16 v25, 0x0

    const/16 v26, 0x0

    const/16 v27, 0x0

    invoke-static/range {v22 .. v30}, Lkotlin/collections/CollectionsKt;->joinToString$default(Ljava/lang/Iterable;Ljava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/CharSequence;ILjava/lang/CharSequence;Lkotlin/jvm/functions/Function1;ILjava/lang/Object;)Ljava/lang/String;

    move-result-object v12

    new-instance v13, Ljava/lang/StringBuilder;

    invoke-direct {v13}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v13, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v13

    const-string v10, "|"

    invoke-virtual {v13, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v13

    invoke-virtual {v13, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v13

    invoke-virtual {v13, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v13

    invoke-virtual {v13, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v12

    invoke-virtual {v12, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v12

    invoke-virtual {v12, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v12

    invoke-virtual {v12, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v12

    invoke-virtual {v12, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v12

    invoke-virtual {v12, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v12

    invoke-virtual {v12, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v12

    invoke-virtual {v12}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v12

    if-eqz v15, :cond_201

    .line 1350
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v23

    .line 1351
    iget-object v13, v1, Lcom/example/data/network/LocalMovieBoxServer;->responseCache:Ljava/util/concurrent/ConcurrentHashMap;

    invoke-virtual {v13, v12}, Ljava/util/concurrent/ConcurrentHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v13

    check-cast v13, Lkotlin/Pair;

    const-wide/32 v25, 0x493e0

    if-eqz v13, :cond_1a6

    .line 1352
    invoke-virtual {v13}, Lkotlin/Pair;->getFirst()Ljava/lang/Object;

    move-result-object v27

    check-cast v27, Ljava/lang/Number;

    invoke-virtual/range {v27 .. v27}, Ljava/lang/Number;->longValue()J

    move-result-wide v27

    sub-long v27, v23, v27

    cmp-long v27, v27, v25

    if-gez v27, :cond_1a6

    .line 1353
    invoke-virtual {v13}, Lkotlin/Pair;->getSecond()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/String;

    return-object v0

    .line 1355
    :cond_1a6
    iget-object v13, v1, Lcom/example/data/network/LocalMovieBoxServer;->diskCachePrefs:Landroid/content/SharedPreferences;

    move-object/from16 v27, v5

    const/4 v5, 0x0

    invoke-interface {v13, v12, v5}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v13

    if-eqz v13, :cond_203

    .line 1357
    move-object/from16 v28, v13

    check-cast v28, Ljava/lang/CharSequence;

    const/16 v32, 0x6

    const/16 v33, 0x0

    const/16 v29, 0x7c

    const/16 v30, 0x0

    const/16 v31, 0x0

    invoke-static/range {v28 .. v33}, Lkotlin/text/StringsKt;->indexOf$default(Ljava/lang/CharSequence;CIZILjava/lang/Object;)I

    move-result v5

    move/from16 v28, v15

    const/4 v15, -0x1

    move-object/from16 v29, v10

    if-eq v5, v15, :cond_207

    const/4 v15, 0x0

    .line 1359
    invoke-virtual {v13, v15, v5}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v10

    const-string v15, "substring(...)"

    invoke-static {v10, v15}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V

    add-int/lit8 v5, v5, 0x1

    .line 1360
    invoke-virtual {v13, v5}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object v5

    invoke-static {v5, v15}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V

    .line 1361
    invoke-static {v10}, Lkotlin/text/StringsKt;->toLongOrNull(Ljava/lang/String;)Ljava/lang/Long;

    move-result-object v10

    if-eqz v10, :cond_1e8

    invoke-virtual {v10}, Ljava/lang/Long;->longValue()J

    move-result-wide v30

    goto :goto_1ea

    :cond_1e8
    const-wide/16 v30, 0x0

    :goto_1ea
    sub-long v23, v23, v30

    cmp-long v10, v23, v25

    if-gez v10, :cond_207

    .line 1363
    iget-object v0, v1, Lcom/example/data/network/LocalMovieBoxServer;->responseCache:Ljava/util/concurrent/ConcurrentHashMap;

    check-cast v0, Ljava/util/Map;

    new-instance v1, Lkotlin/Pair;

    invoke-static/range {v30 .. v31}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v2

    invoke-direct {v1, v2, v5}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V

    invoke-interface {v0, v12, v1}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    return-object v5

    :cond_201
    move-object/from16 v27, v5

    :cond_203
    move-object/from16 v29, v10

    move/from16 v28, v15

    .line 1370
    :cond_207
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v23

    invoke-static/range {v23 .. v24}, Ljava/lang/String;->valueOf(J)Ljava/lang/String;

    move-result-object v5

    .line 1371
    move-object v10, v5

    check-cast v10, Ljava/lang/CharSequence;

    invoke-static {v10}, Lkotlin/text/StringsKt;->reversed(Ljava/lang/CharSequence;)Ljava/lang/CharSequence;

    move-result-object v10

    invoke-virtual {v10}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v10

    .line 1372
    invoke-direct {v1, v10}, Lcom/example/data/network/LocalMovieBoxServer;->md5(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v10

    new-instance v13, Ljava/lang/StringBuilder;

    invoke-direct {v13}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v13, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v13

    const-string v15, ","

    invoke-virtual {v13, v15}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v13

    invoke-virtual {v13, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    .line 1375
    new-instance v13, Ljava/lang/StringBuilder;

    const-string v15, "https://api.inmoviebox.com"

    invoke-direct {v13, v15}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v13, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 1377
    sget-object v13, Lokhttp3/HttpUrl;->Companion:Lokhttp3/HttpUrl$Companion;

    invoke-virtual {v13, v0}, Lokhttp3/HttpUrl$Companion;->get(Ljava/lang/String;)Lokhttp3/HttpUrl;

    move-result-object v0

    invoke-virtual {v0}, Lokhttp3/HttpUrl;->newBuilder()Lokhttp3/HttpUrl$Builder;

    move-result-object v0

    .line 1378
    const-string v13, "host"

    move-object/from16 v15, p3

    invoke-interface {v15, v13}, Ljava/util/Map;->containsKey(Ljava/lang/Object;)Z

    move-result v23

    if-nez v23, :cond_25d

    .line 1379
    const-string v15, "api.inmoviebox.com"

    invoke-virtual {v0, v13, v15}, Lokhttp3/HttpUrl$Builder;->addQueryParameter(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/HttpUrl$Builder;

    .line 1937
    :cond_25d
    invoke-interface/range {p3 .. p3}, Ljava/util/Map;->entrySet()Ljava/util/Set;

    move-result-object v13

    invoke-interface {v13}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v13

    :goto_265
    invoke-interface {v13}, Ljava/util/Iterator;->hasNext()Z

    move-result v15

    if-eqz v15, :cond_287

    invoke-interface {v13}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v15

    check-cast v15, Ljava/util/Map$Entry;

    invoke-interface {v15}, Ljava/util/Map$Entry;->getKey()Ljava/lang/Object;

    move-result-object v23

    move-object/from16 p2, v13

    move-object/from16 v13, v23

    check-cast v13, Ljava/lang/String;

    invoke-interface {v15}, Ljava/util/Map$Entry;->getValue()Ljava/lang/Object;

    move-result-object v15

    check-cast v15, Ljava/lang/String;

    .line 1382
    invoke-virtual {v0, v13, v15}, Lokhttp3/HttpUrl$Builder;->addQueryParameter(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/HttpUrl$Builder;

    move-object/from16 v13, p2

    goto :goto_265

    .line 1384
    :cond_287
    invoke-virtual {v0}, Lokhttp3/HttpUrl$Builder;->build()Lokhttp3/HttpUrl;

    move-result-object v13

    .line 1386
    invoke-virtual {v13}, Lokhttp3/HttpUrl;->queryParameterNames()Ljava/util/Set;

    move-result-object v0

    check-cast v0, Ljava/lang/Iterable;

    invoke-static {v0}, Lkotlin/collections/CollectionsKt;->sorted(Ljava/lang/Iterable;)Ljava/util/List;

    move-result-object v0

    move-object/from16 v30, v0

    check-cast v30, Ljava/lang/Iterable;

    move-object/from16 v31, v27

    check-cast v31, Ljava/lang/CharSequence;

    new-instance v0, Lcom/example/data/network/LocalMovieBoxServer$$ExternalSyntheticLambda1;

    invoke-direct {v0, v13}, Lcom/example/data/network/LocalMovieBoxServer$$ExternalSyntheticLambda1;-><init>(Lokhttp3/HttpUrl;)V

    const/16 v37, 0x1e

    const/16 v38, 0x0

    const/16 v32, 0x0

    const/16 v33, 0x0

    const/16 v34, 0x0

    const/16 v35, 0x0

    move-object/from16 v36, v0

    invoke-static/range {v30 .. v38}, Lkotlin/collections/CollectionsKt;->joinToString$default(Ljava/lang/Iterable;Ljava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/CharSequence;ILjava/lang/CharSequence;Lkotlin/jvm/functions/Function1;ILjava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    .line 1393
    move-object v15, v3

    check-cast v15, Ljava/lang/CharSequence;

    invoke-interface {v15}, Ljava/lang/CharSequence;->length()I

    move-result v15

    move/from16 p2, v15

    const-string v15, "getBytes(...)"

    if-lez p2, :cond_2ec

    move-object/from16 v23, v12

    .line 1394
    sget-object v12, Lkotlin/text/Charsets;->UTF_8:Ljava/nio/charset/Charset;

    invoke-virtual {v3, v12}, Ljava/lang/String;->getBytes(Ljava/nio/charset/Charset;)[B

    move-result-object v12

    invoke-static {v12, v15}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V

    move-object/from16 v24, v10

    .line 1395
    array-length v10, v12

    invoke-static {v10}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v10

    move-object/from16 p2, v10

    .line 1396
    array-length v10, v12

    move-object/from16 p3, v13

    const v13, 0x19000

    invoke-static {v10, v13}, Ljava/lang/Math;->min(II)I

    move-result v10

    const/4 v13, 0x0

    .line 1397
    invoke-static {v12, v13, v10}, Lkotlin/collections/ArraysKt;->copyOfRange([BII)[B

    move-result-object v10

    .line 1398
    invoke-direct {v1, v10}, Lcom/example/data/network/LocalMovieBoxServer;->md5Bytes([B)Ljava/lang/String;

    move-result-object v10

    move-object v12, v10

    move-object/from16 v10, p2

    goto :goto_2f4

    :cond_2ec
    move-object/from16 v24, v10

    move-object/from16 v23, v12

    move-object/from16 p3, v13

    move-object v10, v8

    move-object v12, v10

    .line 1401
    :goto_2f4
    invoke-virtual/range {p3 .. p3}, Lokhttp3/HttpUrl;->encodedPath()Ljava/lang/String;

    move-result-object v13

    move-object/from16 v25, v0

    check-cast v25, Ljava/lang/CharSequence;

    invoke-interface/range {v25 .. v25}, Ljava/lang/CharSequence;->length()I

    move-result v25

    if-lez v25, :cond_316

    move-object/from16 v25, v14

    new-instance v14, Ljava/lang/StringBuilder;

    move-object/from16 v26, v6

    const-string v6, "?"

    invoke-direct {v14, v6}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v14, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    goto :goto_31b

    :cond_316
    move-object/from16 v26, v6

    move-object/from16 v25, v14

    move-object v0, v8

    :goto_31b
    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v6, v13}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 1405
    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v6, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v13, "\napplication/json\napplication/json;charset=UTF-8\n"

    invoke-virtual {v6, v13}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v10, "\n"

    invoke-virtual {v6, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 1409
    :try_start_35d
    const-string v6, "76iRl07s0xSN9jqmEWAt79EBJZulIQIsV64FZr2O"
    :try_end_35f
    .catch Ljava/lang/Exception; {:try_start_35d .. :try_end_35f} :catch_39b

    const/4 v13, 0x0

    .line 1411
    :try_start_360
    invoke-static {v6, v13}, Landroid/util/Base64;->decode(Ljava/lang/String;I)[B

    move-result-object v6
    :try_end_364
    .catch Ljava/lang/Exception; {:try_start_360 .. :try_end_364} :catch_365

    goto :goto_36e

    .line 1413
    :catch_365
    :try_start_365
    sget-object v10, Lkotlin/text/Charsets;->UTF_8:Ljava/nio/charset/Charset;

    invoke-virtual {v6, v10}, Ljava/lang/String;->getBytes(Ljava/nio/charset/Charset;)[B

    move-result-object v6

    invoke-static {v6, v15}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V

    .line 1415
    :goto_36e
    invoke-static {v6}, Lkotlin/jvm/internal/Intrinsics;->checkNotNull(Ljava/lang/Object;)V

    sget-object v10, Lkotlin/text/Charsets;->UTF_8:Ljava/nio/charset/Charset;

    invoke-virtual {v0, v10}, Ljava/lang/String;->getBytes(Ljava/nio/charset/Charset;)[B

    move-result-object v0

    invoke-static {v0, v15}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V

    invoke-direct {v1, v6, v0}, Lcom/example/data/network/LocalMovieBoxServer;->hmacMd5([B[B)[B

    move-result-object v0

    const/4 v6, 0x2

    .line 1416
    invoke-static {v0, v6}, Landroid/util/Base64;->encodeToString([BI)Ljava/lang/String;

    move-result-object v0

    .line 1417
    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v6, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v10, "|2|"

    invoke-virtual {v6, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0
    :try_end_39a
    .catch Ljava/lang/Exception; {:try_start_365 .. :try_end_39a} :catch_39b

    goto :goto_3a4

    :catch_39b
    move-exception v0

    .line 1419
    const-string v6, "Error generating signature"

    check-cast v0, Ljava/lang/Throwable;

    invoke-static {v4, v6, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    move-object v0, v8

    .line 1422
    :goto_3a4
    sget-object v6, Lokhttp3/MediaType;->Companion:Lokhttp3/MediaType$Companion;

    const-string v10, "application/json;charset=UTF-8"

    invoke-virtual {v6, v10}, Lokhttp3/MediaType$Companion;->parse(Ljava/lang/String;)Lokhttp3/MediaType;

    move-result-object v6

    .line 1423
    const-string v12, "POST"

    invoke-static {v2, v12}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v12

    if-nez v12, :cond_3bf

    const-string v12, "PUT"

    invoke-static {v2, v12}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v12

    if-eqz v12, :cond_3bd

    goto :goto_3bf

    :cond_3bd
    const/4 v3, 0x0

    goto :goto_3c5

    .line 1424
    :cond_3bf
    :goto_3bf
    sget-object v12, Lokhttp3/RequestBody;->Companion:Lokhttp3/RequestBody$Companion;

    invoke-virtual {v12, v6, v3}, Lokhttp3/RequestBody$Companion;->create(Lokhttp3/MediaType;Ljava/lang/String;)Lokhttp3/RequestBody;

    move-result-object v3

    :goto_3c5
    const/16 v6, 0x10

    .line 1428
    new-array v6, v6, [Lkotlin/Pair;

    const-string v12, "package_name"

    const-string v13, "com.movieboxpro.android"

    invoke-static {v12, v13}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v12

    const/16 v16, 0x0

    aput-object v12, v6, v16

    .line 1429
    const-string v12, "version_name"

    const-string v13, "16.2.1"

    invoke-static {v12, v13}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v12

    aput-object v12, v6, v18

    const/16 v12, 0x3f52

    .line 1430
    invoke-static {v12}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v12

    const-string v13, "version_code"

    invoke-static {v13, v12}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v12

    const/16 v22, 0x2

    aput-object v12, v6, v22

    .line 1431
    const-string v12, "os"

    const-string v13, "android"

    invoke-static {v12, v13}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v12

    const/4 v13, 0x3

    aput-object v12, v6, v13

    .line 1432
    const-string v12, "os_version"

    const-string v13, "12"

    invoke-static {v12, v13}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v12

    const/4 v13, 0x4

    aput-object v12, v6, v13

    .line 1433
    const-string v12, "install_ch"

    const-string v13, "googleplay"

    invoke-static {v12, v13}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v12

    const/4 v13, 0x5

    aput-object v12, v6, v13

    .line 1434
    const-string v12, "device_id"

    invoke-direct {v1}, Lcom/example/data/network/LocalMovieBoxServer;->getDeviceId()Ljava/lang/String;

    move-result-object v13

    invoke-static {v12, v13}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v12

    const/4 v13, 0x6

    aput-object v12, v6, v13

    .line 1435
    const-string v12, "install_store"

    const-string v13, "googleplay"

    invoke-static {v12, v13}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v12

    const/4 v13, 0x7

    aput-object v12, v6, v13

    .line 1436
    const-string v12, "gaid"

    invoke-static {v12, v8}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v12

    const/16 v13, 0x8

    aput-object v12, v6, v13

    .line 1437
    const-string v12, "brand"

    const-string v13, "google"

    invoke-static {v12, v13}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v12

    const/16 v13, 0x9

    aput-object v12, v6, v13

    .line 1438
    const-string v12, "model"

    const-string v13, "Pixel 6"

    invoke-static {v12, v13}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v12

    const/16 v13, 0xa

    aput-object v12, v6, v13

    .line 1439
    const-string v12, "system_language"

    const-string v13, "en"

    invoke-static {v12, v13}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v12

    const/16 v13, 0xb

    aput-object v12, v6, v13

    .line 1440
    const-string v12, "net"

    const-string v13, "wifi"

    invoke-static {v12, v13}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v12

    const/16 v13, 0xc

    aput-object v12, v6, v13

    .line 1441
    const-string v12, "region"

    invoke-static {v12, v11}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v12

    const/16 v13, 0xd

    aput-object v12, v6, v13

    .line 1442
    const-string v12, "timezone"

    invoke-static {v12, v7}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v7

    const/16 v12, 0xe

    aput-object v7, v6, v12

    const/16 v7, 0xf

    move-object/from16 v12, v26

    .line 1443
    invoke-static {v12, v9}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v9

    aput-object v9, v6, v7

    .line 1427
    invoke-static {v6}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v6

    .line 1445
    invoke-direct {v1, v6}, Lcom/example/data/network/LocalMovieBoxServer;->toJsonString(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v6

    .line 1447
    invoke-virtual {v11}, Ljava/lang/String;->hashCode()I

    move-result v7

    const/16 v9, 0x850

    if-eq v7, v9, :cond_4d1

    const/16 v9, 0x925

    if-eq v7, v9, :cond_4be

    const/16 v9, 0xa43

    if-eq v7, v9, :cond_4b2

    const/16 v9, 0xa9e

    if-eq v7, v9, :cond_4a1

    :goto_49c
    move-object/from16 v9, v20

    move-object/from16 v7, v21

    goto :goto_4db

    :cond_4a1
    move-object/from16 v7, v25

    invoke-virtual {v11, v7}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v7

    if-nez v7, :cond_4aa

    goto :goto_49c

    .line 1449
    :cond_4aa
    const-string v7, "en-US,en;q=0.9"

    move-object v12, v7

    move-object/from16 v9, v20

    move-object/from16 v7, v21

    goto :goto_4e0

    :cond_4b2
    move-object/from16 v7, v21

    .line 1447
    invoke-virtual {v11, v7}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v9

    if-nez v9, :cond_4bb

    goto :goto_4c8

    .line 1451
    :cond_4bb
    const-string v9, "ru-RU,ru;q=0.9,en;q=0.8"

    goto :goto_4cd

    :cond_4be
    move-object/from16 v9, v19

    move-object/from16 v7, v21

    .line 1447
    invoke-virtual {v11, v9}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v9

    if-nez v9, :cond_4cb

    :goto_4c8
    move-object/from16 v9, v20

    goto :goto_4db

    .line 1448
    :cond_4cb
    const-string v9, "en-IN,hi-IN;q=0.9,en;q=0.8"

    :goto_4cd
    move-object v12, v9

    move-object/from16 v9, v20

    goto :goto_4e0

    :cond_4d1
    move-object/from16 v9, v20

    move-object/from16 v7, v21

    .line 1447
    invoke-virtual {v11, v9}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v12

    if-nez v12, :cond_4de

    .line 1452
    :goto_4db
    const-string v12, "en-US,en;q=0.9"

    goto :goto_4e0

    .line 1450
    :cond_4de
    const-string v12, "pt-BR,pt;q=0.9,en;q=0.8"

    .line 1455
    :goto_4e0
    new-instance v13, Lokhttp3/Request$Builder;

    invoke-direct {v13}, Lokhttp3/Request$Builder;-><init>()V

    move-object/from16 v14, p3

    .line 1456
    invoke-virtual {v13, v14}, Lokhttp3/Request$Builder;->url(Lokhttp3/HttpUrl;)Lokhttp3/Request$Builder;

    move-result-object v13

    .line 1457
    const-string v14, "User-Agent"

    const-string v15, "MovieBoxPro/16.2.1 (Android 14; com.community.mbox.in)"

    invoke-virtual {v13, v14, v15}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v13

    .line 1458
    const-string v14, "Accept"

    const-string v15, "application/json"

    invoke-virtual {v13, v14, v15}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v13

    .line 1459
    const-string v14, "Accept-Language"

    invoke-virtual {v13, v14, v12}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v12

    .line 1460
    const-string v13, "Content-Type"

    invoke-virtual {v12, v13, v10}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v10

    .line 1461
    const-string v12, "X-Sign-Version"

    const-string v13, "2.0"

    invoke-virtual {v10, v12, v13}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v10

    .line 1462
    const-string v12, "appid"

    const-string v13, "4U01pxRu278GqCZKY9"

    invoke-virtual {v10, v12, v13}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v10

    .line 1463
    const-string v12, "region"

    invoke-virtual {v10, v12, v11}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v10

    .line 1464
    invoke-static {v11, v9}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v9

    if-eqz v9, :cond_526

    const-string v7, "pt"

    goto :goto_531

    :cond_526
    invoke-static {v11, v7}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v7

    if-eqz v7, :cond_52f

    const-string v7, "ru"

    goto :goto_531

    :cond_52f
    const-string v7, "en"

    :goto_531
    const-string v9, "lang"

    invoke-virtual {v10, v9, v7}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v7

    .line 1465
    const-string v9, "os"

    const-string v10, "android"

    invoke-virtual {v7, v9, v10}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v7

    .line 1466
    const-string v9, "X-Timestamp"

    invoke-virtual {v7, v9, v5}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v5

    .line 1467
    const-string v7, "Referer"

    const-string v9, "https://api.inmoviebox.com/"

    invoke-virtual {v5, v7, v9}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v5

    .line 1468
    const-string v7, "X-Client-Token"

    move-object/from16 v9, v24

    invoke-virtual {v5, v7, v9}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v5

    .line 1469
    const-string v7, "x-tr-signature"

    invoke-virtual {v5, v7, v0}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v0

    .line 1470
    const-string v5, "X-Play-Mode"

    const-string v7, "2"

    invoke-virtual {v0, v5, v7}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v0

    .line 1471
    const-string v5, "X-Client-Info"

    invoke-virtual {v0, v5, v6}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v0

    .line 1473
    const-string v5, "auth_token"

    move-object/from16 v6, v17

    const/4 v12, 0x0

    invoke-interface {v6, v5, v12}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v5

    .line 1474
    move-object v6, v5

    check-cast v6, Ljava/lang/CharSequence;

    if-eqz v6, :cond_57d

    invoke-interface {v6}, Ljava/lang/CharSequence;->length()I

    move-result v6

    if-nez v6, :cond_58e

    .line 1475
    :cond_57d
    sget-object v5, Lcom/example/data/network/LocalMovieBoxServer;->guestToken:Ljava/lang/String;

    check-cast v5, Ljava/lang/CharSequence;

    if-eqz v5, :cond_589

    invoke-interface {v5}, Ljava/lang/CharSequence;->length()I

    move-result v5

    if-nez v5, :cond_58c

    .line 1476
    :cond_589
    invoke-direct {v1}, Lcom/example/data/network/LocalMovieBoxServer;->bootstrapGuestToken()V

    .line 1478
    :cond_58c
    sget-object v5, Lcom/example/data/network/LocalMovieBoxServer;->guestToken:Ljava/lang/String;

    .line 1480
    :cond_58e
    move-object v6, v5

    check-cast v6, Ljava/lang/CharSequence;

    if-eqz v6, :cond_599

    invoke-interface {v6}, Ljava/lang/CharSequence;->length()I

    move-result v6

    if-nez v6, :cond_59b

    .line 1481
    :cond_599
    const-string v5, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOjcwNjU5NDg0MTAyMTM4MTYyMzIsInV0cCI6MSwiZXhwIjoxNzkxNzMyMjMzLCJpYXQiOjE3ODM5NTU5MzN9.7iyEzTj4vWAbOF0oXwNnZ0p3Nc1QaO6K9eMiGFyVfGs"

    .line 1483
    :cond_59b
    new-instance v6, Ljava/lang/StringBuilder;

    const-string v7, "Bearer "

    invoke-direct {v6, v7}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v6, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    const-string v6, "Authorization"

    invoke-virtual {v0, v6, v5}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    .line 1484
    const-string v5, "X-Client-Status"

    const-string v6, "1"

    invoke-virtual {v0, v5, v6}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    .line 1486
    invoke-virtual {v0, v2, v3}, Lokhttp3/Request$Builder;->method(Ljava/lang/String;Lokhttp3/RequestBody;)Lokhttp3/Request$Builder;

    .line 1487
    invoke-virtual {v0}, Lokhttp3/Request$Builder;->build()Lokhttp3/Request;

    move-result-object v0

    .line 1489
    iget-object v2, v1, Lcom/example/data/network/LocalMovieBoxServer;->okHttpClient:Lokhttp3/OkHttpClient;

    invoke-virtual {v2, v0}, Lokhttp3/OkHttpClient;->newCall(Lokhttp3/Request;)Lokhttp3/Call;

    move-result-object v2

    invoke-interface {v2}, Lokhttp3/Call;->execute()Lokhttp3/Response;

    move-result-object v2

    .line 1490
    invoke-virtual {v2}, Lokhttp3/Response;->body()Lokhttp3/ResponseBody;

    move-result-object v3

    if-eqz v3, :cond_5d3

    invoke-virtual {v3}, Lokhttp3/ResponseBody;->string()Ljava/lang/String;

    move-result-object v3

    if-nez v3, :cond_5d4

    :cond_5d3
    move-object v3, v8

    .line 1493
    :cond_5d4
    invoke-virtual {v2}, Lokhttp3/Response;->code()I

    move-result v5

    const/16 v6, 0x191

    if-eq v5, v6, :cond_601

    move-object v5, v3

    check-cast v5, Ljava/lang/CharSequence;

    const-string v6, "JWT token has expired"

    check-cast v6, Ljava/lang/CharSequence;

    const/4 v7, 0x2

    const/4 v12, 0x0

    const/4 v13, 0x0

    invoke-static {v5, v6, v13, v7, v12}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v6

    if-nez v6, :cond_602

    const-string v6, "\"code\":401"

    check-cast v6, Ljava/lang/CharSequence;

    invoke-static {v5, v6, v13, v7, v12}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v6

    if-nez v6, :cond_602

    const-string v6, "\"code\": 401"

    check-cast v6, Ljava/lang/CharSequence;

    invoke-static {v5, v6, v13, v7, v12}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_64b

    goto :goto_602

    :cond_601
    const/4 v12, 0x0

    .line 1494
    :cond_602
    :goto_602
    const-string v3, "401 Expired Token encountered: bootstrapping a fresh guest token..."

    invoke-static {v4, v3}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 1495
    sput-object v12, Lcom/example/data/network/LocalMovieBoxServer;->guestToken:Ljava/lang/String;

    .line 1496
    invoke-direct {v1}, Lcom/example/data/network/LocalMovieBoxServer;->bootstrapGuestToken()V

    .line 1497
    sget-object v3, Lcom/example/data/network/LocalMovieBoxServer;->guestToken:Ljava/lang/String;

    if-nez v3, :cond_612

    const-string v3, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOjcwNjU5NDg0MTAyMTM4MTYyMzIsInV0cCI6MSwiZXhwIjoxNzkxNzMyMjMzLCJpYXQiOjE3ODM5NTU5MzN9.7iyEzTj4vWAbOF0oXwNnZ0p3Nc1QaO6K9eMiGFyVfGs"

    .line 1498
    :cond_612
    invoke-virtual {v0}, Lokhttp3/Request;->newBuilder()Lokhttp3/Request$Builder;

    move-result-object v0

    .line 1499
    new-instance v4, Ljava/lang/StringBuilder;

    const-string v5, "Bearer "

    invoke-direct {v4, v5}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v4, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    const-string v4, "Authorization"

    invoke-virtual {v0, v4, v3}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v0

    .line 1500
    invoke-virtual {v0}, Lokhttp3/Request$Builder;->build()Lokhttp3/Request;

    move-result-object v0

    .line 1501
    invoke-virtual {v2}, Lokhttp3/Response;->close()V

    .line 1502
    iget-object v2, v1, Lcom/example/data/network/LocalMovieBoxServer;->okHttpClient:Lokhttp3/OkHttpClient;

    invoke-virtual {v2, v0}, Lokhttp3/OkHttpClient;->newCall(Lokhttp3/Request;)Lokhttp3/Call;

    move-result-object v0

    invoke-interface {v0}, Lokhttp3/Call;->execute()Lokhttp3/Response;

    move-result-object v0

    .line 1503
    invoke-virtual {v0}, Lokhttp3/Response;->body()Lokhttp3/ResponseBody;

    move-result-object v0

    if-eqz v0, :cond_64a

    invoke-virtual {v0}, Lokhttp3/ResponseBody;->string()Ljava/lang/String;

    move-result-object v0

    if-nez v0, :cond_649

    goto :goto_64a

    :cond_649
    move-object v8, v0

    :cond_64a
    :goto_64a
    move-object v3, v8

    :cond_64b
    if-eqz v28, :cond_6a7

    .line 1506
    move-object v0, v3

    check-cast v0, Ljava/lang/CharSequence;

    invoke-interface {v0}, Ljava/lang/CharSequence;->length()I

    move-result v2

    if-lez v2, :cond_6a7

    const-string v2, "Internal Server Error"

    check-cast v2, Ljava/lang/CharSequence;

    const/4 v6, 0x2

    const/4 v12, 0x0

    const/4 v13, 0x0

    invoke-static {v0, v2, v13, v6, v12}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v2

    if-nez v2, :cond_6a7

    const-string v2, "JWT token has expired"

    check-cast v2, Ljava/lang/CharSequence;

    invoke-static {v0, v2, v13, v6, v12}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_6a7

    .line 1507
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v4

    .line 1508
    iget-object v0, v1, Lcom/example/data/network/LocalMovieBoxServer;->responseCache:Ljava/util/concurrent/ConcurrentHashMap;

    check-cast v0, Ljava/util/Map;

    new-instance v2, Lkotlin/Pair;

    invoke-static {v4, v5}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v6

    invoke-direct {v2, v6, v3}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V

    move-object/from16 v6, v23

    invoke-interface {v0, v6, v2}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 1509
    iget-object v0, v1, Lcom/example/data/network/LocalMovieBoxServer;->diskCachePrefs:Landroid/content/SharedPreferences;

    invoke-interface {v0}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    .line 1510
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v1, v4, v5}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v1

    move-object/from16 v2, v29

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-interface {v0, v6, v1}, Landroid/content/SharedPreferences$Editor;->putString(Ljava/lang/String;Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    .line 1511
    invoke-interface {v0}, Landroid/content/SharedPreferences$Editor;->apply()V

    :cond_6a7
    return-object v3
.end method

.method static synthetic makeOfficialRequest$default(Lcom/example/data/network/LocalMovieBoxServer;Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;
    .registers 7

    and-int/lit8 p6, p5, 0x4

    if-eqz p6, :cond_8

    .line 1339
    invoke-static {}, Lkotlin/collections/MapsKt;->emptyMap()Ljava/util/Map;

    move-result-object p3

    :cond_8
    and-int/lit8 p5, p5, 0x8

    if-eqz p5, :cond_e

    .line 1340
    const-string p4, ""

    .line 1336
    :cond_e
    invoke-direct {p0, p1, p2, p3, p4}, Lcom/example/data/network/LocalMovieBoxServer;->makeOfficialRequest(Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;)Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method static final makeOfficialRequest$lambda$31(Ljava/util/Map$Entry;)Ljava/lang/CharSequence;
    .registers 3

    const-string v0, "it"

    invoke-static {p0, v0}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullParameter(Ljava/lang/Object;Ljava/lang/String;)V

    .line 1348
    invoke-interface {p0}, Ljava/util/Map$Entry;->getKey()Ljava/lang/Object;

    move-result-object v0

    invoke-interface {p0}, Ljava/util/Map$Entry;->getValue()Ljava/lang/Object;

    move-result-object p0

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    check-cast p0, Ljava/lang/CharSequence;

    return-object p0
.end method

.method static final makeOfficialRequest$lambda$33(Lokhttp3/HttpUrl;Ljava/lang/String;)Ljava/lang/CharSequence;
    .registers 3

    const-string v0, "name"

    invoke-static {p1, v0}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullParameter(Ljava/lang/Object;Ljava/lang/String;)V

    .line 1387
    invoke-virtual {p0, p1}, Lokhttp3/HttpUrl;->queryParameter(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p0

    if-nez p0, :cond_d

    const-string p0, ""

    .line 1388
    :cond_d
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string v0, "="

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    check-cast p0, Ljava/lang/CharSequence;

    return-object p0
.end method

.method private final mapItem(Ljava/util/Map;)Ljava/util/Map;
    .registers 25
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/Map<",
            "Ljava/lang/String;",
            "+",
            "Ljava/lang/Object;",
            ">;)",
            "Ljava/util/Map<",
            "Ljava/lang/String;",
            "Ljava/lang/Object;",
            ">;"
        }
    .end annotation

    move-object/from16 v0, p1

    .line 1258
    const-string v1, "subject"

    invoke-interface {v0, v1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    instance-of v2, v1, Ljava/util/Map;

    const/4 v3, 0x0

    if-eqz v2, :cond_10

    check-cast v1, Ljava/util/Map;

    goto :goto_11

    :cond_10
    move-object v1, v3

    :goto_11
    if-nez v1, :cond_14

    move-object v1, v0

    .line 1259
    :cond_14
    const-string v2, "subjectId"

    invoke-interface {v1, v2}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    const-string v5, "id"

    const-string v6, ""

    if-nez v4, :cond_27

    invoke-interface {v1, v5}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    if-nez v4, :cond_27

    move-object v4, v6

    :cond_27
    invoke-virtual {v4}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v4

    .line 1260
    move-object v7, v4

    check-cast v7, Ljava/lang/CharSequence;

    invoke-interface {v7}, Ljava/lang/CharSequence;->length()I

    move-result v7

    if-nez v7, :cond_35

    return-object v3

    .line 1263
    :cond_35
    const-string v7, "title"

    invoke-interface {v1, v7}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v8

    const-string v9, "Unknown"

    const-string v10, "name"

    if-nez v8, :cond_bc

    .line 1264
    invoke-interface {v1, v10}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v8

    if-nez v8, :cond_bc

    .line 1265
    const-string v8, "subjectName"

    invoke-interface {v1, v8}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v8

    if-nez v8, :cond_bc

    .line 1266
    const-string v8, "subject_name"

    invoke-interface {v1, v8}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v8

    if-nez v8, :cond_bc

    .line 1267
    const-string v8, "categoryName"

    invoke-interface {v1, v8}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v8

    if-nez v8, :cond_bc

    .line 1268
    const-string v8, "content"

    invoke-interface {v1, v8}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v8

    if-nez v8, :cond_bc

    .line 1269
    const-string v8, "keyword"

    invoke-interface {v1, v8}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v8

    if-nez v8, :cond_bc

    .line 1270
    const-string v8, "keywordName"

    invoke-interface {v1, v8}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v8

    if-nez v8, :cond_bc

    .line 1271
    const-string v8, "itemName"

    invoke-interface {v1, v8}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v8

    if-nez v8, :cond_bc

    .line 1272
    const-string v8, "show_name"

    invoke-interface {v1, v8}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v8

    if-nez v8, :cond_bc

    .line 1273
    const-string v8, "showTitle"

    invoke-interface {v1, v8}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v8

    if-nez v8, :cond_bc

    .line 1274
    const-string v8, "titleName"

    invoke-interface {v1, v8}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v8

    if-nez v8, :cond_bc

    .line 1275
    const-string v8, "title_en"

    invoke-interface {v1, v8}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v8

    if-nez v8, :cond_bc

    .line 1276
    const-string v8, "tag"

    invoke-interface {v1, v8}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v8

    if-nez v8, :cond_bc

    .line 1277
    const-string v8, "label"

    invoke-interface {v1, v8}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v8

    if-nez v8, :cond_bc

    .line 1278
    invoke-interface {v0, v7}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v8

    if-nez v8, :cond_bc

    .line 1279
    invoke-interface {v0, v10}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v8

    if-nez v8, :cond_bc

    move-object v8, v9

    .line 1281
    :cond_bc
    invoke-virtual {v8}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v0

    .line 1284
    const-string v8, "poster"

    invoke-interface {v1, v8}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    .line 1285
    instance-of v12, v11, Ljava/util/Map;

    const-string v13, "url"

    if-eqz v12, :cond_db

    .line 1286
    check-cast v11, Ljava/util/Map;

    invoke-interface {v11, v13}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    if-eqz v11, :cond_e2

    invoke-virtual {v11}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v11

    if-nez v11, :cond_e3

    goto :goto_e2

    .line 1287
    :cond_db
    instance-of v12, v11, Ljava/lang/String;

    if-eqz v12, :cond_e2

    .line 1288
    check-cast v11, Ljava/lang/String;

    goto :goto_e3

    :cond_e2
    :goto_e2
    move-object v11, v6

    .line 1290
    :cond_e3
    :goto_e3
    move-object v12, v11

    check-cast v12, Ljava/lang/CharSequence;

    invoke-interface {v12}, Ljava/lang/CharSequence;->length()I

    move-result v12

    const-string v14, "cover"

    if-nez v12, :cond_10d

    .line 1291
    invoke-interface {v1, v14}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v12

    .line 1292
    instance-of v15, v12, Ljava/util/Map;

    if-eqz v15, :cond_106

    .line 1293
    check-cast v12, Ljava/util/Map;

    invoke-interface {v12, v13}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    if-eqz v11, :cond_104

    invoke-virtual {v11}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v11

    if-nez v11, :cond_10d

    :cond_104
    move-object v11, v6

    goto :goto_10d

    .line 1294
    :cond_106
    instance-of v15, v12, Ljava/lang/String;

    if-eqz v15, :cond_10d

    .line 1295
    move-object v11, v12

    check-cast v11, Ljava/lang/String;

    .line 1298
    :cond_10d
    :goto_10d
    move-object v12, v11

    check-cast v12, Ljava/lang/CharSequence;

    invoke-interface {v12}, Ljava/lang/CharSequence;->length()I

    move-result v12

    if-nez v12, :cond_13f

    .line 1299
    const-string v12, "horizontalPoster"

    invoke-interface {v1, v12}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v12

    if-nez v12, :cond_124

    const-string v12, "horizontalCover"

    invoke-interface {v1, v12}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v12

    .line 1300
    :cond_124
    instance-of v15, v12, Ljava/util/Map;

    if-eqz v15, :cond_138

    .line 1301
    check-cast v12, Ljava/util/Map;

    invoke-interface {v12, v13}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    if-eqz v11, :cond_136

    invoke-virtual {v11}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v11

    if-nez v11, :cond_13f

    :cond_136
    move-object v11, v6

    goto :goto_13f

    .line 1302
    :cond_138
    instance-of v13, v12, Ljava/lang/String;

    if-eqz v13, :cond_13f

    .line 1303
    move-object v11, v12

    check-cast v11, Ljava/lang/String;

    .line 1306
    :cond_13f
    :goto_13f
    const-string v12, "//"

    const/4 v13, 0x0

    const/4 v15, 0x2

    invoke-static {v11, v12, v13, v15, v3}, Lkotlin/text/StringsKt;->startsWith$default(Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Z

    move-result v12

    if-eqz v12, :cond_15b

    .line 1307
    new-instance v12, Ljava/lang/StringBuilder;

    move-object/from16 p0, v3

    const-string v3, "https:"

    invoke-direct {v12, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v12, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v11

    goto :goto_15d

    :cond_15b
    move-object/from16 p0, v3

    .line 1310
    :goto_15d
    const-string v3, "imdbRatingValue"

    invoke-interface {v1, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    const-string v12, "score"

    if-nez v3, :cond_17f

    const-string v3, "imdbRate"

    invoke-interface {v1, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    if-nez v3, :cond_17f

    const-string v3, "starRating"

    invoke-interface {v1, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    if-nez v3, :cond_17f

    invoke-interface {v1, v12}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    if-nez v3, :cond_17f

    const-string v3, "8.5"

    :cond_17f
    invoke-virtual {v3}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v3

    move/from16 p1, v15

    .line 1311
    const-string v15, "releaseDate"

    invoke-interface {v1, v15}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v15

    const-string v13, "year"

    const-string v17, "2026"

    move-object/from16 v18, v6

    const-string v6, "releaseTime"

    if-nez v15, :cond_1a3

    invoke-interface {v1, v6}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v15

    if-nez v15, :cond_1a3

    invoke-interface {v1, v13}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v15

    if-nez v15, :cond_1a3

    move-object/from16 v15, v17

    :cond_1a3
    invoke-virtual {v15}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v15

    move-object/from16 v19, v6

    .line 1312
    invoke-virtual {v15}, Ljava/lang/String;->length()I

    move-result v6

    move-object/from16 v20, v13

    const/4 v13, 0x4

    if-lt v6, v13, :cond_1bd

    const/4 v6, 0x0

    invoke-virtual {v15, v6, v13}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v15

    const-string v6, "substring(...)"

    invoke-static {v15, v6}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V

    goto :goto_1bf

    :cond_1bd
    move-object/from16 v15, v17

    .line 1313
    :goto_1bf
    const-string v6, "duration"

    invoke-interface {v1, v6}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    move/from16 v17, v13

    const-string v13, "runtime"

    if-nez v6, :cond_1db

    invoke-interface {v1, v13}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    if-nez v6, :cond_1db

    const-string v6, "minute"

    invoke-interface {v1, v6}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    if-nez v6, :cond_1db

    move-object/from16 v6, v18

    .line 1315
    :cond_1db
    move-object/from16 v21, v0

    check-cast v21, Ljava/lang/CharSequence;

    invoke-interface/range {v21 .. v21}, Ljava/lang/CharSequence;->length()I

    move-result v22

    if-nez v22, :cond_1e7

    goto/16 :goto_294

    :cond_1e7
    invoke-static {v0, v9}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v9

    if-nez v9, :cond_294

    invoke-static/range {v21 .. v21}, Lkotlin/text/StringsKt;->isBlank(Ljava/lang/CharSequence;)Z

    move-result v9

    if-nez v9, :cond_294

    move-object v9, v11

    check-cast v9, Ljava/lang/CharSequence;

    invoke-interface {v9}, Ljava/lang/CharSequence;->length()I

    move-result v21

    if-nez v21, :cond_1fe

    goto/16 :goto_294

    :cond_1fe
    invoke-static {v9}, Lkotlin/text/StringsKt;->isBlank(Ljava/lang/CharSequence;)Z

    move-result v9

    if-eqz v9, :cond_206

    goto/16 :goto_294

    :cond_206
    const/16 v9, 0xd

    .line 1320
    new-array v9, v9, [Lkotlin/Pair;

    invoke-static {v2, v4}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    const/16 v16, 0x0

    aput-object v2, v9, v16

    .line 1321
    invoke-static {v5, v4}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    const/4 v4, 0x1

    aput-object v2, v9, v4

    .line 1322
    invoke-static {v7, v0}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    aput-object v2, v9, p1

    const/4 v2, 0x3

    .line 1323
    invoke-static {v10, v0}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    aput-object v0, v9, v2

    .line 1324
    invoke-static {v8, v11}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    aput-object v0, v9, v17

    const/4 v0, 0x5

    .line 1325
    invoke-static {v14, v11}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    aput-object v2, v9, v0

    .line 1326
    const-string v0, "rating"

    invoke-static {v0, v3}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    const/4 v2, 0x6

    aput-object v0, v9, v2

    const/4 v0, 0x7

    .line 1327
    invoke-static {v12, v3}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    aput-object v2, v9, v0

    const/16 v0, 0x8

    move-object/from16 v2, v20

    .line 1328
    invoke-static {v2, v15}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    aput-object v2, v9, v0

    const/16 v0, 0x9

    move-object/from16 v2, v19

    .line 1329
    invoke-static {v2, v15}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    aput-object v2, v9, v0

    .line 1330
    const-string v0, "subjectType"

    invoke-interface {v1, v0}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    if-nez v2, :cond_26b

    const-string v2, "type"

    invoke-interface {v1, v2}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    if-nez v2, :cond_26b

    invoke-static {v4}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    :cond_26b
    invoke-static {v0, v2}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    const/16 v2, 0xa

    aput-object v0, v9, v2

    const/16 v0, 0xb

    .line 1331
    invoke-static {v13, v6}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    aput-object v2, v9, v0

    .line 1332
    const-string v0, "description"

    invoke-interface {v1, v0}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    if-nez v1, :cond_286

    move-object/from16 v6, v18

    goto :goto_287

    :cond_286
    move-object v6, v1

    :goto_287
    invoke-static {v0, v6}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    const/16 v1, 0xc

    aput-object v0, v9, v1

    .line 1319
    invoke-static {v9}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v0

    return-object v0

    :cond_294
    :goto_294
    return-object p0
.end method

.method private final md5(Ljava/lang/String;)Ljava/lang/String;
    .registers 12

    .line 1620
    const-string p0, "MD5"

    invoke-static {p0}, Ljava/security/MessageDigest;->getInstance(Ljava/lang/String;)Ljava/security/MessageDigest;

    move-result-object p0

    .line 1621
    sget-object v0, Lkotlin/text/Charsets;->UTF_8:Ljava/nio/charset/Charset;

    invoke-virtual {p1, v0}, Ljava/lang/String;->getBytes(Ljava/nio/charset/Charset;)[B

    move-result-object p1

    const-string v0, "getBytes(...)"

    invoke-static {p1, v0}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V

    invoke-virtual {p0, p1}, Ljava/security/MessageDigest;->digest([B)[B

    move-result-object v1

    .line 1622
    invoke-static {v1}, Lkotlin/jvm/internal/Intrinsics;->checkNotNull(Ljava/lang/Object;)V

    const-string p0, ""

    move-object v2, p0

    check-cast v2, Ljava/lang/CharSequence;

    new-instance v7, Lcom/example/data/network/LocalMovieBoxServer$$ExternalSyntheticLambda2;

    invoke-direct {v7}, Lcom/example/data/network/LocalMovieBoxServer$$ExternalSyntheticLambda2;-><init>()V

    const/16 v8, 0x1e

    const/4 v9, 0x0

    const/4 v3, 0x0

    const/4 v4, 0x0

    const/4 v5, 0x0

    const/4 v6, 0x0

    invoke-static/range {v1 .. v9}, Lkotlin/collections/ArraysKt;->joinToString$default([BLjava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/CharSequence;ILjava/lang/CharSequence;Lkotlin/jvm/functions/Function1;ILjava/lang/Object;)Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method static final md5$lambda$36(B)Ljava/lang/CharSequence;
    .registers 2

    .line 1622
    sget-object v0, Lkotlin/jvm/internal/StringCompanionObject;->INSTANCE:Lkotlin/jvm/internal/StringCompanionObject;

    invoke-static {p0}, Ljava/lang/Byte;->valueOf(B)Ljava/lang/Byte;

    move-result-object p0

    filled-new-array {p0}, [Ljava/lang/Object;

    move-result-object p0

    const/4 v0, 0x1

    invoke-static {p0, v0}, Ljava/util/Arrays;->copyOf([Ljava/lang/Object;I)[Ljava/lang/Object;

    move-result-object p0

    const-string v0, "%02x"

    invoke-static {v0, p0}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p0

    const-string v0, "format(...)"

    invoke-static {p0, v0}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V

    check-cast p0, Ljava/lang/CharSequence;

    return-object p0
.end method

.method private final md5Bytes([B)Ljava/lang/String;
    .registers 11

    .line 1626
    const-string p0, "MD5"

    invoke-static {p0}, Ljava/security/MessageDigest;->getInstance(Ljava/lang/String;)Ljava/security/MessageDigest;

    move-result-object p0

    .line 1627
    invoke-virtual {p0, p1}, Ljava/security/MessageDigest;->digest([B)[B

    move-result-object v0

    .line 1628
    invoke-static {v0}, Lkotlin/jvm/internal/Intrinsics;->checkNotNull(Ljava/lang/Object;)V

    const-string p0, ""

    move-object v1, p0

    check-cast v1, Ljava/lang/CharSequence;

    new-instance v6, Lcom/example/data/network/LocalMovieBoxServer$$ExternalSyntheticLambda5;

    invoke-direct {v6}, Lcom/example/data/network/LocalMovieBoxServer$$ExternalSyntheticLambda5;-><init>()V

    const/16 v7, 0x1e

    const/4 v8, 0x0

    const/4 v2, 0x0

    const/4 v3, 0x0

    const/4 v4, 0x0

    const/4 v5, 0x0

    invoke-static/range {v0 .. v8}, Lkotlin/collections/ArraysKt;->joinToString$default([BLjava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/CharSequence;ILjava/lang/CharSequence;Lkotlin/jvm/functions/Function1;ILjava/lang/Object;)Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method static final md5Bytes$lambda$37(B)Ljava/lang/CharSequence;
    .registers 2

    .line 1628
    sget-object v0, Lkotlin/jvm/internal/StringCompanionObject;->INSTANCE:Lkotlin/jvm/internal/StringCompanionObject;

    invoke-static {p0}, Ljava/lang/Byte;->valueOf(B)Ljava/lang/Byte;

    move-result-object p0

    filled-new-array {p0}, [Ljava/lang/Object;

    move-result-object p0

    const/4 v0, 0x1

    invoke-static {p0, v0}, Ljava/util/Arrays;->copyOf([Ljava/lang/Object;I)[Ljava/lang/Object;

    move-result-object p0

    const-string v0, "%02x"

    invoke-static {v0, p0}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p0

    const-string v0, "format(...)"

    invoke-static {p0, v0}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V

    check-cast p0, Ljava/lang/CharSequence;

    return-object p0
.end method

.method private final parseJsonToMap(Ljava/lang/String;)Ljava/util/Map;
    .registers 3
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/String;",
            ")",
            "Ljava/util/Map<",
            "Ljava/lang/String;",
            "Ljava/lang/Object;",
            ">;"
        }
    .end annotation

    .line 1749
    move-object v0, p1

    check-cast v0, Ljava/lang/CharSequence;

    invoke-static {v0}, Lkotlin/text/StringsKt;->trim(Ljava/lang/CharSequence;)Ljava/lang/CharSequence;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v0

    check-cast v0, Ljava/lang/CharSequence;

    invoke-interface {v0}, Ljava/lang/CharSequence;->length()I

    move-result v0

    if-nez v0, :cond_18

    invoke-static {}, Lkotlin/collections/MapsKt;->emptyMap()Ljava/util/Map;

    move-result-object p0

    return-object p0

    .line 1751
    :cond_18
    :try_start_18
    new-instance v0, Lorg/json/JSONObject;

    invoke-direct {v0, p1}, Lorg/json/JSONObject;-><init>(Ljava/lang/String;)V

    .line 1752
    invoke-direct {p0, v0}, Lcom/example/data/network/LocalMovieBoxServer;->jsonToMap(Lorg/json/JSONObject;)Ljava/util/Map;

    move-result-object p0
    :try_end_21
    .catch Ljava/lang/Exception; {:try_start_18 .. :try_end_21} :catch_22

    return-object p0

    .line 1754
    :catch_22
    invoke-static {}, Lkotlin/collections/MapsKt;->emptyMap()Ljava/util/Map;

    move-result-object p0

    return-object p0
.end method

.method private final parseQueryParams(Ljava/lang/String;)Ljava/util/Map;
    .registers 15
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/String;",
            ")",
            "Ljava/util/Map<",
            "Ljava/lang/String;",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation

    .line 1172
    new-instance p0, Ljava/util/LinkedHashMap;

    invoke-direct {p0}, Ljava/util/LinkedHashMap;-><init>()V

    check-cast p0, Ljava/util/Map;

    .line 1173
    move-object v0, p1

    check-cast v0, Ljava/lang/CharSequence;

    invoke-interface {v0}, Ljava/lang/CharSequence;->length()I

    move-result p1

    if-nez p1, :cond_11

    return-object p0

    :cond_11
    const/4 p1, 0x1

    .line 1174
    new-array v1, p1, [Ljava/lang/String;

    const-string v2, "&"

    const/4 v6, 0x0

    aput-object v2, v1, v6

    const/4 v4, 0x6

    const/4 v5, 0x0

    const/4 v2, 0x0

    const/4 v3, 0x0

    invoke-static/range {v0 .. v5}, Lkotlin/text/StringsKt;->split$default(Ljava/lang/CharSequence;[Ljava/lang/String;ZIILjava/lang/Object;)Ljava/util/List;

    move-result-object v0

    check-cast v0, Ljava/lang/Iterable;

    .line 1906
    invoke-interface {v0}, Ljava/lang/Iterable;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_27
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_67

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/String;

    .line 1175
    move-object v7, v1

    check-cast v7, Ljava/lang/CharSequence;

    new-array v8, p1, [Ljava/lang/String;

    const-string v1, "="

    aput-object v1, v8, v6

    const/4 v11, 0x2

    const/4 v12, 0x0

    const/4 v9, 0x0

    const/4 v10, 0x2

    invoke-static/range {v7 .. v12}, Lkotlin/text/StringsKt;->split$default(Ljava/lang/CharSequence;[Ljava/lang/String;ZIILjava/lang/Object;)Ljava/util/List;

    move-result-object v1

    .line 1176
    invoke-interface {v1, v6}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/String;

    const-string v3, "UTF-8"

    invoke-static {v2, v3}, Ljava/net/URLDecoder;->decode(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    .line 1177
    invoke-interface {v1}, Ljava/util/List;->size()I

    move-result v4

    if-le v4, p1, :cond_61

    invoke-interface {v1, p1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/String;

    invoke-static {v1, v3}, Ljava/net/URLDecoder;->decode(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    goto :goto_63

    :cond_61
    const-string v1, ""

    .line 1178
    :goto_63
    invoke-interface {p0, v2, v1}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    goto :goto_27

    :cond_67
    return-object p0
.end method

.method private final routeRequest(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lkotlin/Pair;
    .registers 53
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/String;",
            "Ljava/lang/String;",
            "Ljava/lang/String;",
            "Ljava/lang/String;",
            ")",
            "Lkotlin/Pair<",
            "Ljava/lang/Integer;",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation

    move-object/from16 v1, p0

    move-object/from16 v8, p2

    move-object/from16 v0, p4

    .line 160
    const-string v9, "\"}"

    const-string v10, "LocalMovieBoxServer"

    .line 0
    const-string v11, "Empty list for tabId "

    const-string v2, "{\"status\": \"error\", \"message\": \""

    const-string v12, "Category fallback triggered: "

    const-string v3, "{\"status\": \"error\", \"message\": \""

    const-string v4, "Register rawResponse: "

    const-string v5, "Register payload: "

    const-string v6, "{\"status\": \"success\", \"url\": \"file://"

    const-string v7, "{\"status\": \"error\", \"message\": \""

    const-string v13, "{\"code\":-1,\"message\":\""

    const-string v14, "{\"subjectId\":\""

    const-string v15, "{\"code\": 500, \"message\": \""

    move-object/from16 p1, v12

    const-string v12, "{\"code\": 500, \"message\": \""

    move-object/from16 v16, v11

    const-string v11, "{\"status\": \"error\", \"message\": \""

    move-object/from16 v17, v6

    const-string v6, "{\"code\": 500, \"message\": \""

    move-object/from16 v18, v6

    const-string v6, "{\"code\": 500, \"message\": \""

    move-object/from16 v19, v13

    move-object/from16 v13, p3

    .line 161
    :try_start_34
    invoke-direct {v1, v13}, Lcom/example/data/network/LocalMovieBoxServer;->parseQueryParams(Ljava/lang/String;)Ljava/util/Map;

    move-result-object v13

    move-object/from16 v20, v14

    .line 164
    const-string v14, "/clear-cache"

    invoke-static {v8, v14}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v14

    move-object/from16 v21, v4

    const/16 v4, 0xc8

    if-eqz v14, :cond_65

    .line 165
    iget-object v0, v1, Lcom/example/data/network/LocalMovieBoxServer;->responseCache:Ljava/util/concurrent/ConcurrentHashMap;

    invoke-virtual {v0}, Ljava/util/concurrent/ConcurrentHashMap;->clear()V

    .line 166
    iget-object v0, v1, Lcom/example/data/network/LocalMovieBoxServer;->diskCachePrefs:Landroid/content/SharedPreferences;

    invoke-interface {v0}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    invoke-interface {v0}, Landroid/content/SharedPreferences$Editor;->clear()Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    invoke-interface {v0}, Landroid/content/SharedPreferences$Editor;->apply()V

    .line 168
    new-instance v0, Lkotlin/Pair;

    invoke-static {v4}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    const-string v2, "{\"code\": 0, \"message\": \"Cache cleared\"}"

    invoke-direct {v0, v1, v2}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V

    goto/16 :goto_2c3a

    .line 171
    :cond_65
    const-string v14, "/docs"

    invoke-static {v8, v14}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v14

    if-nez v14, :cond_2be1

    const-string v14, "/docs/"

    invoke-static {v8, v14}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v14

    if-eqz v14, :cond_77

    goto/16 :goto_2be1

    .line 175
    :cond_77
    const-string v14, "/user-info"

    invoke-static {v8, v14}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v14
    :try_end_7d
    .catch Ljava/lang/Exception; {:try_start_34 .. :try_end_7d} :catch_2bfa

    const-string v4, "nickname"

    move-object/from16 v22, v4

    const-string v4, "data"

    move-object/from16 v25, v4

    const-string v4, ""

    move-object/from16 v26, v4

    const/4 v4, 0x0

    move-object/from16 v29, v5

    const/4 v5, 0x0

    if-eqz v14, :cond_39d

    .line 176
    :try_start_8f
    iget-object v0, v1, Lcom/example/data/network/LocalMovieBoxServer;->context:Landroid/content/Context;

    const-string v2, "moviebox_prefs"

    invoke-virtual {v0, v2, v4}, Landroid/content/Context;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v0

    .line 177
    const-string v2, "auth_token"

    invoke-interface {v0, v2, v5}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    .line 178
    check-cast v2, Ljava/lang/CharSequence;

    if-eqz v2, :cond_38e

    invoke-interface {v2}, Ljava/lang/CharSequence;->length()I

    move-result v2
    :try_end_a5
    .catch Ljava/lang/Exception; {:try_start_8f .. :try_end_a5} :catch_2bfa

    if-nez v2, :cond_a9

    goto/16 :goto_38e

    .line 182
    :cond_a9
    :try_start_a9
    const-string v2, "GET"

    const-string v3, "/wefeed-mobile-bff/user-api/profile/v2"
    :try_end_ad
    .catch Ljava/lang/Exception; {:try_start_a9 .. :try_end_ad} :catch_c7

    const/16 v6, 0xc

    const/4 v7, 0x0

    move v11, v4

    const/4 v4, 0x0

    move-object v12, v5

    const/4 v5, 0x0

    move-object v13, v12

    move-object/from16 v15, v22

    move-object/from16 v14, v26

    const/16 p3, 0xc8

    const/16 v24, 0x3

    const/16 v28, 0x1

    move v12, v11

    move-object/from16 v11, v25

    :try_start_c2
    invoke-static/range {v1 .. v7}, Lcom/example/data/network/LocalMovieBoxServer;->makeOfficialRequest$default(Lcom/example/data/network/LocalMovieBoxServer;Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;

    move-result-object v4
    :try_end_c6
    .catch Ljava/lang/Exception; {:try_start_c2 .. :try_end_c6} :catch_d5

    goto :goto_d6

    :catch_c7
    move v12, v4

    move-object v13, v5

    move-object/from16 v15, v22

    move-object/from16 v11, v25

    move-object/from16 v14, v26

    const/16 p3, 0xc8

    const/16 v24, 0x3

    const/16 v28, 0x1

    :catch_d5
    move-object v4, v14

    .line 186
    :goto_d6
    :try_start_d6
    invoke-direct {v1, v4}, Lcom/example/data/network/LocalMovieBoxServer;->parseJsonToMap(Ljava/lang/String;)Ljava/util/Map;

    move-result-object v2
    :try_end_da
    .catch Ljava/lang/Exception; {:try_start_d6 .. :try_end_da} :catch_db

    goto :goto_df

    .line 187
    :catch_db
    :try_start_db
    invoke-static {}, Lkotlin/collections/MapsKt;->emptyMap()Ljava/util/Map;

    move-result-object v2

    .line 189
    :goto_df
    invoke-interface {v2, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    instance-of v3, v2, Ljava/util/Map;

    if-eqz v3, :cond_eb

    move-object v5, v2

    check-cast v5, Ljava/util/Map;

    goto :goto_ec

    :cond_eb
    move-object v5, v13

    :goto_ec
    if-nez v5, :cond_10a

    move-object v2, v1

    check-cast v2, Lcom/example/data/network/LocalMovieBoxServer;

    .line 190
    const-string v2, "user_info_json"

    invoke-interface {v0, v2, v13}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 191
    move-object v2, v0

    check-cast v2, Ljava/lang/CharSequence;

    if-eqz v2, :cond_109

    invoke-interface {v2}, Ljava/lang/CharSequence;->length()I

    move-result v2
    :try_end_100
    .catch Ljava/lang/Exception; {:try_start_db .. :try_end_100} :catch_2bfa

    if-nez v2, :cond_103

    goto :goto_109

    .line 193
    :cond_103
    :try_start_103
    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->parseJsonToMap(Ljava/lang/String;)Ljava/util/Map;

    move-result-object v0
    :try_end_107
    .catch Ljava/lang/Exception; {:try_start_103 .. :try_end_107} :catch_109

    move-object v5, v0

    goto :goto_10a

    :catch_109
    :cond_109
    :goto_109
    move-object v5, v13

    :cond_10a
    :goto_10a
    if-eqz v5, :cond_113

    .line 198
    :try_start_10c
    const-string v0, "userInfo"

    invoke-interface {v5, v0}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    goto :goto_114

    :cond_113
    move-object v0, v13

    :goto_114
    instance-of v2, v0, Ljava/util/Map;

    if-eqz v2, :cond_11b

    check-cast v0, Ljava/util/Map;

    goto :goto_11c

    :cond_11b
    move-object v0, v13

    :goto_11c
    if-eqz v5, :cond_125

    .line 199
    const-string v2, "vipInfo"

    invoke-interface {v5, v2}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    goto :goto_126

    :cond_125
    move-object v2, v13

    :goto_126
    instance-of v3, v2, Ljava/util/Map;

    if-eqz v3, :cond_12d

    check-cast v2, Ljava/util/Map;

    goto :goto_12e

    :cond_12d
    move-object v2, v13

    :goto_12e
    if-eqz v5, :cond_137

    .line 200
    const-string v3, "mySubject"

    invoke-interface {v5, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    goto :goto_138

    :cond_137
    move-object v3, v13

    :goto_138
    instance-of v4, v3, Ljava/util/Map;

    if-eqz v4, :cond_13f

    check-cast v3, Ljava/util/Map;

    goto :goto_140

    :cond_13f
    move-object v3, v13

    :goto_140
    if-eqz v5, :cond_149

    .line 201
    const-string v4, "favoriteInfo"

    invoke-interface {v5, v4}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    goto :goto_14a

    :cond_149
    move-object v4, v13

    :goto_14a
    instance-of v6, v4, Ljava/util/Map;

    if-eqz v6, :cond_151

    check-cast v4, Ljava/util/Map;

    goto :goto_152

    :cond_151
    move-object v4, v13

    :goto_152
    const/16 v6, 0xc

    .line 204
    new-array v6, v6, [Lkotlin/Pair;

    const-string v7, "username"

    if-eqz v0, :cond_162

    const-string v11, "username"

    invoke-interface {v0, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    if-nez v11, :cond_184

    :cond_162
    if-eqz v5, :cond_16b

    const-string v11, "userName"

    invoke-interface {v5, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    goto :goto_16c

    :cond_16b
    move-object v11, v13

    :goto_16c
    if-nez v11, :cond_184

    if-eqz v5, :cond_175

    invoke-interface {v5, v15}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    goto :goto_176

    :cond_175
    move-object v11, v13

    :goto_176
    if-nez v11, :cond_184

    if-eqz v0, :cond_17f

    invoke-interface {v0, v15}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    goto :goto_180

    :cond_17f
    move-object v11, v13

    :goto_180
    if-nez v11, :cond_184

    const-string v11, "User"

    :cond_184
    invoke-static {v7, v11}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v7

    aput-object v7, v6, v12

    if-eqz v0, :cond_192

    .line 205
    invoke-interface {v0, v15}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v7

    if-nez v7, :cond_19e

    :cond_192
    if-eqz v5, :cond_199

    invoke-interface {v5, v15}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v7

    goto :goto_19a

    :cond_199
    move-object v7, v13

    :goto_19a
    if-nez v7, :cond_19e

    const-string v7, "User"

    :cond_19e
    invoke-static {v15, v7}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v7

    aput-object v7, v6, v28

    .line 206
    const-string v7, "avatar"

    if-eqz v0, :cond_1b0

    const-string v11, "avatar"

    invoke-interface {v0, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    if-nez v11, :cond_1bd

    :cond_1b0
    if-eqz v5, :cond_1b9

    const-string v11, "avatarUrl"

    invoke-interface {v5, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    goto :goto_1ba

    :cond_1b9
    move-object v11, v13

    :goto_1ba
    if-nez v11, :cond_1bd

    move-object v11, v14

    :cond_1bd
    invoke-static {v7, v11}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v7

    const/16 v27, 0x2

    aput-object v7, v6, v27

    .line 207
    const-string v7, "email"

    if-eqz v0, :cond_1d1

    const-string v11, "mail"

    invoke-interface {v0, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    if-nez v11, :cond_1de

    :cond_1d1
    if-eqz v5, :cond_1da

    const-string v11, "email"

    invoke-interface {v5, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    goto :goto_1db

    :cond_1da
    move-object v11, v13

    :goto_1db
    if-nez v11, :cond_1de

    move-object v11, v14

    :cond_1de
    invoke-static {v7, v11}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v7

    aput-object v7, v6, v24

    .line 208
    const-string v7, "vip"

    if-eqz v2, :cond_1f7

    const-string v11, "isActive"

    invoke-interface {v2, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    invoke-static/range {v28 .. v28}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v15

    invoke-static {v11, v15}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v11

    goto :goto_1f8

    :cond_1f7
    move v11, v12

    :goto_1f8
    if-nez v11, :cond_227

    if-eqz v5, :cond_21d

    const-string v11, "vip"

    invoke-interface {v5, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    if-eqz v11, :cond_21d

    invoke-virtual {v11}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v11

    if-eqz v11, :cond_21d

    invoke-static {v11}, Lkotlin/text/StringsKt;->toDoubleOrNull(Ljava/lang/String;)Ljava/lang/Double;

    move-result-object v11

    if-eqz v11, :cond_21d

    move/from16 p1, v12

    invoke-virtual {v11}, Ljava/lang/Double;->doubleValue()D

    move-result-wide v12

    double-to-int v11, v12

    move/from16 v12, v28

    if-ne v11, v12, :cond_21f

    const/4 v11, 0x1

    goto :goto_221

    :cond_21d
    move/from16 p1, v12

    :cond_21f
    move/from16 v11, p1

    :goto_221
    if-eqz v11, :cond_224

    goto :goto_229

    :cond_224
    move/from16 v11, p1

    goto :goto_22a

    :cond_227
    move/from16 p1, v12

    :goto_229
    const/4 v11, 0x1

    :goto_22a
    invoke-static {v11}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v11

    invoke-static {v7, v11}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v7

    const/4 v11, 0x4

    aput-object v7, v6, v11

    .line 209
    const-string v7, "userId"

    if-eqz v0, :cond_244

    const-string v12, "userId"

    invoke-interface {v0, v12}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    if-nez v0, :cond_242

    goto :goto_244

    :cond_242
    move-object v15, v0

    goto :goto_252

    :cond_244
    :goto_244
    if-eqz v5, :cond_24e

    const-string v0, "userId"

    invoke-interface {v5, v0}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    move-object v15, v0

    goto :goto_24f

    :cond_24e
    const/4 v15, 0x0

    :goto_24f
    if-nez v15, :cond_252

    move-object v15, v14

    :cond_252
    :goto_252
    invoke-static {v7, v15}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    const/4 v5, 0x5

    aput-object v0, v6, v5

    .line 210
    const-string v0, "vipExpire"

    if-eqz v2, :cond_267

    const-string v5, "expiryDate"

    invoke-interface {v2, v5}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    if-nez v5, :cond_266

    goto :goto_267

    :cond_266
    move-object v14, v5

    :cond_267
    :goto_267
    invoke-static {v0, v14}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    const/4 v5, 0x6

    aput-object v0, v6, v5

    .line 211
    const-string v0, "vipDaysLeft"

    if-eqz v2, :cond_28c

    const-string v5, "daysLeft"

    invoke-interface {v2, v5}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    if-eqz v5, :cond_28c

    invoke-virtual {v5}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v5

    if-eqz v5, :cond_28c

    invoke-static {v5}, Lkotlin/text/StringsKt;->toDoubleOrNull(Ljava/lang/String;)Ljava/lang/Double;

    move-result-object v5

    if-eqz v5, :cond_28c

    invoke-virtual {v5}, Ljava/lang/Double;->doubleValue()D

    move-result-wide v12

    double-to-int v5, v12

    goto :goto_28e

    :cond_28c
    move/from16 v5, p1

    :goto_28e
    invoke-static {v5}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    invoke-static {v0, v5}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    const/4 v5, 0x7

    aput-object v0, v6, v5

    .line 212
    const-string v0, "vipPoint"

    if-eqz v2, :cond_2b7

    const-string v5, "point"

    invoke-interface {v2, v5}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    if-eqz v2, :cond_2b7

    invoke-virtual {v2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v2

    if-eqz v2, :cond_2b7

    invoke-static {v2}, Lkotlin/text/StringsKt;->toDoubleOrNull(Ljava/lang/String;)Ljava/lang/Double;

    move-result-object v2

    if-eqz v2, :cond_2b7

    invoke-virtual {v2}, Ljava/lang/Double;->doubleValue()D

    move-result-wide v12

    double-to-int v2, v12

    goto :goto_2b9

    :cond_2b7
    move/from16 v2, p1

    :goto_2b9
    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-static {v0, v2}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    const/16 v2, 0x8

    aput-object v0, v6, v2

    .line 213
    const-string v0, "wantToSeeCount"

    if-eqz v3, :cond_2e3

    const-string v2, "wantToSeeCount"

    invoke-interface {v3, v2}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    if-eqz v2, :cond_2e3

    invoke-virtual {v2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v2

    if-eqz v2, :cond_2e3

    invoke-static {v2}, Lkotlin/text/StringsKt;->toDoubleOrNull(Ljava/lang/String;)Ljava/lang/Double;

    move-result-object v2

    if-eqz v2, :cond_2e3

    invoke-virtual {v2}, Ljava/lang/Double;->doubleValue()D

    move-result-wide v12

    double-to-int v2, v12

    goto :goto_2e5

    :cond_2e3
    move/from16 v2, p1

    :goto_2e5
    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-static {v0, v2}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    const/16 v2, 0x9

    aput-object v0, v6, v2

    .line 214
    const-string v0, "haveSeenCount"

    if-eqz v3, :cond_30f

    const-string v2, "haveSeenCount"

    invoke-interface {v3, v2}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    if-eqz v2, :cond_30f

    invoke-virtual {v2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v2

    if-eqz v2, :cond_30f

    invoke-static {v2}, Lkotlin/text/StringsKt;->toDoubleOrNull(Ljava/lang/String;)Ljava/lang/Double;

    move-result-object v2

    if-eqz v2, :cond_30f

    invoke-virtual {v2}, Ljava/lang/Double;->doubleValue()D

    move-result-wide v2

    double-to-int v2, v2

    goto :goto_311

    :cond_30f
    move/from16 v2, p1

    :goto_311
    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-static {v0, v2}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    const/16 v2, 0xa

    aput-object v0, v6, v2

    .line 215
    const-string v0, "favoriteCount"

    if-eqz v4, :cond_33b

    const-string v2, "favoriteCount"

    invoke-interface {v4, v2}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    if-eqz v2, :cond_33b

    invoke-virtual {v2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v2

    if-eqz v2, :cond_33b

    invoke-static {v2}, Lkotlin/text/StringsKt;->toDoubleOrNull(Ljava/lang/String;)Ljava/lang/Double;

    move-result-object v2

    if-eqz v2, :cond_33b

    invoke-virtual {v2}, Ljava/lang/Double;->doubleValue()D

    move-result-wide v2

    double-to-int v4, v2

    goto :goto_33d

    :cond_33b
    move/from16 v4, p1

    :goto_33d
    invoke-static {v4}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-static {v0, v2}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    const/16 v2, 0xb

    aput-object v0, v6, v2

    .line 203
    invoke-static {v6}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v0

    .line 219
    new-array v2, v11, [Lkotlin/Pair;

    const-string v3, "logged_in"

    const/16 v28, 0x1

    invoke-static/range {v28 .. v28}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v4

    invoke-static {v3, v4}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v3

    aput-object v3, v2, p1

    .line 220
    const-string v3, "mode"

    const-string v4, "Official Account"

    invoke-static {v3, v4}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v3

    const/16 v28, 0x1

    aput-object v3, v2, v28

    .line 221
    const-string v3, "user"

    invoke-static {v3, v0}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    aput-object v0, v2, v27

    .line 222
    const-string v0, "session_id"

    const-string v3, "local_session"

    invoke-static {v0, v3}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    aput-object v0, v2, v24

    .line 218
    invoke-static {v2}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v0

    .line 224
    new-instance v2, Lkotlin/Pair;

    invoke-static/range {p3 .. p3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->toJsonString(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    invoke-direct {v2, v3, v0}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V

    goto/16 :goto_ca4

    :cond_38e
    :goto_38e
    const/16 p3, 0xc8

    .line 179
    new-instance v0, Lkotlin/Pair;

    invoke-static/range {p3 .. p3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    const-string v2, "{\"logged_in\": false, \"mode\": \"Guest Access\", \"session_id\": \"local_session\"}"

    invoke-direct {v0, v1, v2}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V

    goto/16 :goto_2c3a

    :cond_39d
    move-object v4, v11

    move-object/from16 v5, v22

    move-object/from16 v11, v25

    const/16 p3, 0xc8

    const/16 v24, 0x3

    .line 228
    const-string v14, "/request-otp"

    invoke-static {v8, v14}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v14
    :try_end_3ac
    .catch Ljava/lang/Exception; {:try_start_10c .. :try_end_3ac} :catch_2bfa

    move/from16 v22, v14

    const-string v14, "POST"

    move-object/from16 v25, v15

    const-string v15, "code"

    if-eqz v22, :cond_494

    .line 229
    :try_start_3b6
    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->parseJsonToMap(Ljava/lang/String;)Ljava/util/Map;

    move-result-object v0

    .line 230
    const-string v2, "account"

    invoke-interface {v0, v2}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    if-eqz v2, :cond_3c8

    invoke-virtual {v2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v4

    if-nez v4, :cond_3ca

    :cond_3c8
    move-object/from16 v4, v26

    .line 231
    :cond_3ca
    const-string v2, "type"

    invoke-interface {v0, v2}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    if-eqz v0, :cond_3e4

    invoke-virtual {v0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v0

    if-eqz v0, :cond_3e4

    invoke-static {v0}, Lkotlin/text/StringsKt;->toDoubleOrNull(Ljava/lang/String;)Ljava/lang/Double;

    move-result-object v0

    if-eqz v0, :cond_3e4

    invoke-virtual {v0}, Ljava/lang/Double;->doubleValue()D

    move-result-wide v2

    double-to-int v0, v2

    goto :goto_3e5

    :cond_3e4
    const/4 v0, 0x1

    :goto_3e5
    const/4 v11, 0x4

    .line 234
    new-array v2, v11, [Lkotlin/Pair;

    const-string v3, "package_name"

    const-string v5, "com.community.mbox.in"

    invoke-static {v3, v5}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v3

    const/4 v11, 0x0

    aput-object v3, v2, v11

    .line 235
    const-string v3, "type"

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v0

    invoke-static {v3, v0}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    const/16 v28, 0x1

    aput-object v0, v2, v28

    .line 236
    const-string v0, "mail"

    invoke-static {v0, v4}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    const/16 v27, 0x2

    aput-object v0, v2, v27

    .line 237
    const-string v0, "authType"

    invoke-static/range {v28 .. v28}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    invoke-static {v0, v3}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    aput-object v0, v2, v24

    .line 233
    invoke-static {v2}, Lkotlin/collections/MapsKt;->mutableMapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v0

    .line 240
    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->toJsonString(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    .line 241
    const-string v2, "/wefeed-mobile-bff/user-api/get-sms-code"

    invoke-static {}, Lkotlin/collections/MapsKt;->emptyMap()Ljava/util/Map;

    move-result-object v3

    invoke-direct {v1, v14, v2, v3, v0}, Lcom/example/data/network/LocalMovieBoxServer;->makeOfficialRequest(Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 243
    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->parseJsonToMap(Ljava/lang/String;)Ljava/util/Map;

    move-result-object v0

    .line 244
    invoke-interface {v0, v15}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    if-eqz v1, :cond_445

    invoke-virtual {v1}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v1

    if-eqz v1, :cond_445

    invoke-static {v1}, Lkotlin/text/StringsKt;->toDoubleOrNull(Ljava/lang/String;)Ljava/lang/Double;

    move-result-object v1

    if-eqz v1, :cond_445

    invoke-virtual {v1}, Ljava/lang/Double;->doubleValue()D

    move-result-wide v1

    double-to-int v1, v1

    goto :goto_446

    :cond_445
    const/4 v1, -0x1

    :goto_446
    move/from16 v2, p3

    if-eqz v1, :cond_487

    if-eq v1, v2, :cond_487

    .line 248
    const-string v1, "msg"

    invoke-interface {v0, v1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    if-eqz v1, :cond_45a

    invoke-virtual {v1}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v1

    if-nez v1, :cond_469

    :cond_45a
    const-string v1, "message"

    invoke-interface {v0, v1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    if-eqz v0, :cond_467

    invoke-virtual {v0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v1

    goto :goto_469

    :cond_467
    const-string v1, "Failed to send OTP"

    .line 249
    :cond_469
    :goto_469
    new-instance v0, Lkotlin/Pair;

    const/16 v2, 0x190

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3, v7}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, v2, v1}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V

    goto/16 :goto_2c3a

    .line 246
    :cond_487
    new-instance v0, Lkotlin/Pair;

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    const-string v2, "{\"status\": \"success\", \"msg\": \"OTP Sent\"}"

    invoke-direct {v0, v1, v2}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V

    goto/16 :goto_2c3a

    :cond_494
    const/16 v27, 0x2

    const/16 v28, 0x1

    const/16 v34, 0x0

    .line 253
    const-string v7, "/login"

    invoke-static {v8, v7}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v7

    if-eqz v7, :cond_67d

    .line 254
    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->parseJsonToMap(Ljava/lang/String;)Ljava/util/Map;

    move-result-object v0

    .line 255
    const-string v2, "account"

    invoke-interface {v0, v2}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    if-eqz v2, :cond_4b4

    invoke-virtual {v2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v4

    if-nez v4, :cond_4b6

    :cond_4b4
    move-object/from16 v4, v26

    .line 256
    :cond_4b6
    const-string v2, "password"

    invoke-interface {v0, v2}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    if-eqz v2, :cond_4c4

    invoke-virtual {v2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v2

    if-nez v2, :cond_4c6

    :cond_4c4
    move-object/from16 v2, v26

    .line 257
    :cond_4c6
    const-string v6, "authType"

    invoke-interface {v0, v6}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    if-eqz v0, :cond_4dd

    invoke-virtual {v0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v0

    if-eqz v0, :cond_4dd

    invoke-static {v0}, Lkotlin/text/StringsKt;->toDoubleOrNull(Ljava/lang/String;)Ljava/lang/Double;

    move-result-object v0

    if-eqz v0, :cond_4dd

    invoke-virtual {v0}, Ljava/lang/Double;->doubleValue()D

    .line 259
    :cond_4dd
    invoke-direct {v1, v2}, Lcom/example/data/network/LocalMovieBoxServer;->md5(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const/4 v2, 0x5

    .line 261
    new-array v2, v2, [Lkotlin/Pair;

    const-string v6, "password"

    invoke-static {v6, v0}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    aput-object v0, v2, v34

    .line 262
    const-string v0, "package_name"

    const-string v6, "com.community.mbox.in"

    invoke-static {v0, v6}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    aput-object v0, v2, v28

    .line 263
    const-string v0, "authType"

    invoke-static/range {v28 .. v28}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v6

    invoke-static {v0, v6}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    aput-object v0, v2, v27

    .line 264
    const-string v0, "type"

    invoke-static/range {v34 .. v34}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v6

    invoke-static {v0, v6}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    aput-object v0, v2, v24

    .line 265
    const-string v0, "mail"

    invoke-static {v0, v4}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    const/16 v23, 0x4

    aput-object v0, v2, v23

    .line 260
    invoke-static {v2}, Lkotlin/collections/MapsKt;->mutableMapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v0

    .line 268
    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->toJsonString(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    .line 269
    const-string v2, "/wefeed-mobile-bff/user-api/login"

    invoke-static {}, Lkotlin/collections/MapsKt;->emptyMap()Ljava/util/Map;

    move-result-object v6

    invoke-direct {v1, v14, v2, v6, v0}, Lcom/example/data/network/LocalMovieBoxServer;->makeOfficialRequest(Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 271
    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->parseJsonToMap(Ljava/lang/String;)Ljava/util/Map;

    move-result-object v0

    .line 272
    invoke-interface {v0, v15}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    if-eqz v2, :cond_546

    invoke-virtual {v2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v2

    if-eqz v2, :cond_546

    invoke-static {v2}, Lkotlin/text/StringsKt;->toDoubleOrNull(Ljava/lang/String;)Ljava/lang/Double;

    move-result-object v2

    if-eqz v2, :cond_546

    invoke-virtual {v2}, Ljava/lang/Double;->doubleValue()D

    move-result-wide v6

    double-to-int v2, v6

    goto :goto_547

    :cond_546
    const/4 v2, -0x1

    .line 273
    :goto_547
    invoke-interface {v0, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    instance-of v7, v6, Ljava/util/Map;

    if-eqz v7, :cond_552

    check-cast v6, Ljava/util/Map;

    goto :goto_553

    :cond_552
    const/4 v6, 0x0

    :goto_553
    if-eqz v6, :cond_562

    .line 274
    const-string v7, "token"

    invoke-interface {v6, v7}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v7

    if-eqz v7, :cond_562

    invoke-virtual {v7}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v7

    goto :goto_563

    :cond_562
    const/4 v7, 0x0

    :goto_563
    if-eqz v2, :cond_569

    const/16 v11, 0xc8

    if-ne v2, v11, :cond_642

    .line 276
    :cond_569
    move-object v2, v7

    check-cast v2, Ljava/lang/CharSequence;

    if-eqz v2, :cond_642

    invoke-interface {v2}, Ljava/lang/CharSequence;->length()I

    move-result v2

    if-nez v2, :cond_576

    goto/16 :goto_642

    .line 277
    :cond_576
    iget-object v0, v1, Lcom/example/data/network/LocalMovieBoxServer;->context:Landroid/content/Context;

    const-string v2, "moviebox_prefs"

    move/from16 v11, v34

    invoke-virtual {v0, v2, v11}, Landroid/content/Context;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v0

    .line 278
    invoke-interface {v0}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    .line 279
    const-string v2, "auth_token"

    invoke-interface {v0, v2, v7}, Landroid/content/SharedPreferences$Editor;->putString(Ljava/lang/String;Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    .line 280
    const-string v2, "user_info_json"

    invoke-direct {v1, v6}, Lcom/example/data/network/LocalMovieBoxServer;->toJsonString(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v3

    invoke-interface {v0, v2, v3}, Landroid/content/SharedPreferences$Editor;->putString(Ljava/lang/String;Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    .line 281
    invoke-interface {v0}, Landroid/content/SharedPreferences$Editor;->apply()V

    move/from16 v2, v27

    .line 284
    new-array v0, v2, [Lkotlin/Pair;

    const-string v2, "status"

    const-string v3, "success"

    invoke-static {v2, v3}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    const/16 v34, 0x0

    aput-object v2, v0, v34

    .line 285
    const-string v2, "user"

    const/4 v3, 0x6

    .line 286
    new-array v3, v3, [Lkotlin/Pair;

    const-string v7, "username"

    const-string v11, "userName"

    invoke-interface {v6, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    if-nez v11, :cond_5bd

    invoke-interface {v6, v5}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    if-nez v11, :cond_5bd

    move-object v11, v4

    :cond_5bd
    invoke-static {v7, v11}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v7

    const/16 v34, 0x0

    aput-object v7, v3, v34

    .line 287
    invoke-interface {v6, v5}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v7

    if-nez v7, :cond_5cc

    move-object v7, v4

    :cond_5cc
    invoke-static {v5, v7}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v5

    aput-object v5, v3, v28

    .line 288
    const-string v5, "avatar"

    const-string v7, "avatarUrl"

    invoke-interface {v6, v7}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v7

    if-nez v7, :cond_5de

    move-object/from16 v7, v26

    :cond_5de
    invoke-static {v5, v7}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v5

    const/16 v27, 0x2

    aput-object v5, v3, v27

    .line 289
    const-string v5, "email"

    const-string v7, "email"

    invoke-interface {v6, v7}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v7

    if-nez v7, :cond_5f1

    goto :goto_5f2

    :cond_5f1
    move-object v4, v7

    :goto_5f2
    invoke-static {v5, v4}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v4

    aput-object v4, v3, v24

    .line 290
    const-string v4, "vip"

    const-string v5, "vip"

    invoke-interface {v6, v5}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    if-nez v5, :cond_608

    const/16 v34, 0x0

    invoke-static/range {v34 .. v34}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    :cond_608
    invoke-static {v4, v5}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v4

    const/16 v23, 0x4

    aput-object v4, v3, v23

    .line 291
    const-string v4, "userId"

    const-string v5, "userId"

    invoke-interface {v6, v5}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    if-nez v5, :cond_61c

    move-object/from16 v5, v26

    :cond_61c
    invoke-static {v4, v5}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v4

    const/4 v5, 0x5

    aput-object v4, v3, v5

    .line 285
    invoke-static {v3}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v3

    invoke-static {v2, v3}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    aput-object v2, v0, v28

    .line 283
    invoke-static {v0}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v0

    .line 294
    new-instance v2, Lkotlin/Pair;

    const/16 v11, 0xc8

    invoke-static {v11}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->toJsonString(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    invoke-direct {v2, v3, v0}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V

    goto/16 :goto_ca4

    .line 296
    :cond_642
    :goto_642
    const-string v1, "msg"

    invoke-interface {v0, v1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    if-eqz v1, :cond_650

    invoke-virtual {v1}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v1

    if-nez v1, :cond_65f

    :cond_650
    const-string v1, "message"

    invoke-interface {v0, v1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    if-eqz v0, :cond_65d

    invoke-virtual {v0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v1

    goto :goto_65f

    :cond_65d
    const-string v1, "Login failed"

    .line 297
    :cond_65f
    :goto_65f
    new-instance v0, Lkotlin/Pair;

    const/16 v2, 0x190

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v4, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, v2, v1}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V

    goto/16 :goto_2c3a

    .line 301
    :cond_67d
    const-string v3, "/register"

    invoke-static {v8, v3}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_868

    .line 302
    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->parseJsonToMap(Ljava/lang/String;)Ljava/util/Map;

    move-result-object v0

    .line 303
    const-string v3, "account"

    invoke-interface {v0, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    if-eqz v3, :cond_697

    invoke-virtual {v3}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v4

    if-nez v4, :cond_699

    :cond_697
    move-object/from16 v4, v26

    .line 304
    :cond_699
    const-string v3, "password"

    invoke-interface {v0, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    if-eqz v3, :cond_6a7

    invoke-virtual {v3}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v3

    if-nez v3, :cond_6a9

    :cond_6a7
    move-object/from16 v3, v26

    .line 305
    :cond_6a9
    const-string v6, "otp"

    invoke-interface {v0, v6}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    if-eqz v6, :cond_6b7

    invoke-virtual {v6}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v6

    if-nez v6, :cond_6b9

    :cond_6b7
    move-object/from16 v6, v26

    .line 306
    :cond_6b9
    const-string v7, "authType"

    invoke-interface {v0, v7}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    if-eqz v0, :cond_6d0

    invoke-virtual {v0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v0

    if-eqz v0, :cond_6d0

    invoke-static {v0}, Lkotlin/text/StringsKt;->toDoubleOrNull(Ljava/lang/String;)Ljava/lang/Double;

    move-result-object v0

    if-eqz v0, :cond_6d0

    invoke-virtual {v0}, Ljava/lang/Double;->doubleValue()D

    .line 308
    :cond_6d0
    invoke-direct {v1, v3}, Lcom/example/data/network/LocalMovieBoxServer;->md5(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const/4 v3, 0x6

    .line 310
    new-array v3, v3, [Lkotlin/Pair;

    const-string v7, "password"

    invoke-static {v7, v0}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    const/16 v34, 0x0

    aput-object v0, v3, v34

    .line 311
    const-string v0, "verificationCode"

    invoke-static {v0, v6}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    aput-object v0, v3, v28

    .line 312
    const-string v0, "package_name"

    const-string v6, "com.community.mbox.in"

    invoke-static {v0, v6}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    const/16 v27, 0x2

    aput-object v0, v3, v27

    .line 313
    const-string v0, "authType"

    invoke-static/range {v28 .. v28}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v6

    invoke-static {v0, v6}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    aput-object v0, v3, v24

    .line 314
    const-string v0, "type"

    invoke-static/range {v28 .. v28}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v6

    invoke-static {v0, v6}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    const/16 v23, 0x4

    aput-object v0, v3, v23

    .line 315
    const-string v0, "mail"

    invoke-static {v0, v4}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    const/4 v6, 0x5

    aput-object v0, v3, v6

    .line 309
    invoke-static {v3}, Lkotlin/collections/MapsKt;->mutableMapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v0

    .line 318
    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->toJsonString(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    .line 319
    new-instance v3, Ljava/lang/StringBuilder;

    move-object/from16 v6, v29

    invoke-direct {v3, v6}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v10, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 320
    const-string v3, "/wefeed-mobile-bff/user-api/register"

    invoke-static {}, Lkotlin/collections/MapsKt;->emptyMap()Ljava/util/Map;

    move-result-object v6

    invoke-direct {v1, v14, v3, v6, v0}, Lcom/example/data/network/LocalMovieBoxServer;->makeOfficialRequest(Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 321
    new-instance v3, Ljava/lang/StringBuilder;

    move-object/from16 v6, v21

    invoke-direct {v3, v6}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v10, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 323
    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->parseJsonToMap(Ljava/lang/String;)Ljava/util/Map;

    move-result-object v0

    .line 324
    invoke-interface {v0, v15}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    if-eqz v3, :cond_76a

    invoke-virtual {v3}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v3

    if-eqz v3, :cond_76a

    invoke-static {v3}, Lkotlin/text/StringsKt;->toDoubleOrNull(Ljava/lang/String;)Ljava/lang/Double;

    move-result-object v3

    if-eqz v3, :cond_76a

    invoke-virtual {v3}, Ljava/lang/Double;->doubleValue()D

    move-result-wide v6

    double-to-int v3, v6

    goto :goto_76b

    :cond_76a
    const/4 v3, -0x1

    .line 325
    :goto_76b
    invoke-interface {v0, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    instance-of v7, v6, Ljava/util/Map;

    if-eqz v7, :cond_776

    check-cast v6, Ljava/util/Map;

    goto :goto_777

    :cond_776
    const/4 v6, 0x0

    :goto_777
    if-eqz v3, :cond_7b8

    const/16 v11, 0xc8

    if-eq v3, v11, :cond_7b8

    .line 341
    const-string v1, "msg"

    invoke-interface {v0, v1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    if-eqz v1, :cond_78b

    invoke-virtual {v1}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v1

    if-nez v1, :cond_79a

    :cond_78b
    const-string v1, "message"

    invoke-interface {v0, v1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    if-eqz v0, :cond_798

    invoke-virtual {v0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v1

    goto :goto_79a

    :cond_798
    const-string v1, "Registration failed"

    .line 342
    :cond_79a
    :goto_79a
    new-instance v0, Lkotlin/Pair;

    const/16 v3, 0x190

    invoke-static {v3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v4, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, v3, v1}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V

    goto/16 :goto_2c3a

    :cond_7b8
    const/4 v2, 0x2

    .line 329
    new-array v0, v2, [Lkotlin/Pair;

    const-string v2, "status"

    const-string v3, "success"

    invoke-static {v2, v3}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    const/16 v34, 0x0

    aput-object v2, v0, v34

    .line 330
    const-string v2, "user"

    const/4 v3, 0x6

    .line 331
    new-array v3, v3, [Lkotlin/Pair;

    const-string v7, "username"

    if-eqz v6, :cond_7d8

    const-string v11, "userName"

    invoke-interface {v6, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    if-nez v11, :cond_7d9

    :cond_7d8
    move-object v11, v4

    :cond_7d9
    invoke-static {v7, v11}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v7

    const/16 v34, 0x0

    aput-object v7, v3, v34

    if-eqz v6, :cond_7e9

    .line 332
    invoke-interface {v6, v5}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v7

    if-nez v7, :cond_7ea

    :cond_7e9
    move-object v7, v4

    :cond_7ea
    invoke-static {v5, v7}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v5

    aput-object v5, v3, v28

    .line 333
    const-string v5, "avatar"

    if-eqz v6, :cond_7fc

    const-string v7, "avatarUrl"

    invoke-interface {v6, v7}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v7

    if-nez v7, :cond_7fe

    :cond_7fc
    move-object/from16 v7, v26

    :cond_7fe
    invoke-static {v5, v7}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v5

    const/16 v27, 0x2

    aput-object v5, v3, v27

    .line 334
    const-string v5, "email"

    if-eqz v6, :cond_814

    const-string v7, "email"

    invoke-interface {v6, v7}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v7

    if-nez v7, :cond_813

    goto :goto_814

    :cond_813
    move-object v4, v7

    :cond_814
    :goto_814
    invoke-static {v5, v4}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v4

    aput-object v4, v3, v24

    .line 335
    const-string v4, "vip"

    if-eqz v6, :cond_826

    const-string v5, "vip"

    invoke-interface {v6, v5}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    if-nez v5, :cond_82c

    :cond_826
    const/16 v34, 0x0

    invoke-static/range {v34 .. v34}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    :cond_82c
    invoke-static {v4, v5}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v4

    const/16 v23, 0x4

    aput-object v4, v3, v23

    .line 336
    const-string v4, "userId"

    if-eqz v6, :cond_840

    const-string v5, "userId"

    invoke-interface {v6, v5}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    if-nez v5, :cond_842

    :cond_840
    move-object/from16 v5, v26

    :cond_842
    invoke-static {v4, v5}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v4

    const/4 v5, 0x5

    aput-object v4, v3, v5

    .line 330
    invoke-static {v3}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v3

    invoke-static {v2, v3}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    aput-object v2, v0, v28

    .line 328
    invoke-static {v0}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v0

    .line 339
    new-instance v2, Lkotlin/Pair;

    const/16 v11, 0xc8

    invoke-static {v11}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->toJsonString(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    invoke-direct {v2, v3, v0}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V

    goto/16 :goto_ca4

    .line 346
    :cond_868
    const-string v2, "/logout"

    invoke-static {v8, v2}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_89b

    .line 347
    iget-object v0, v1, Lcom/example/data/network/LocalMovieBoxServer;->context:Landroid/content/Context;

    const-string v1, "moviebox_prefs"

    const/4 v11, 0x0

    invoke-virtual {v0, v1, v11}, Landroid/content/Context;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v0

    .line 348
    invoke-interface {v0}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    const-string v1, "auth_token"

    invoke-interface {v0, v1}, Landroid/content/SharedPreferences$Editor;->remove(Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    const-string v1, "user_info_json"

    invoke-interface {v0, v1}, Landroid/content/SharedPreferences$Editor;->remove(Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    invoke-interface {v0}, Landroid/content/SharedPreferences$Editor;->apply()V

    .line 349
    new-instance v0, Lkotlin/Pair;

    const/16 v11, 0xc8

    invoke-static {v11}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    const-string v2, "{\"status\": \"success\"}"

    invoke-direct {v0, v1, v2}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V

    goto/16 :goto_2c3a

    .line 352
    :cond_89b
    const-string v2, "/modify"

    invoke-static {v8, v2}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v2
    :try_end_8a1
    .catch Ljava/lang/Exception; {:try_start_3b6 .. :try_end_8a1} :catch_2bfa

    if-eqz v2, :cond_921

    .line 354
    :try_start_8a3
    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->parseJsonToMap(Ljava/lang/String;)Ljava/util/Map;

    move-result-object v0

    .line 355
    invoke-interface {v0, v5}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    if-eqz v2, :cond_8b3

    invoke-virtual {v2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v4

    if-nez v4, :cond_8b5

    :cond_8b3
    move-object/from16 v4, v26

    .line 356
    :cond_8b5
    const-string v2, "avatar"

    invoke-interface {v0, v2}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    if-eqz v0, :cond_8c3

    invoke-virtual {v0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v0

    if-nez v0, :cond_8c5

    :cond_8c3
    move-object/from16 v0, v26

    :cond_8c5
    move/from16 v12, v28

    .line 359
    new-array v2, v12, [Lkotlin/Pair;

    invoke-static {v5, v4}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v3

    const/16 v34, 0x0

    aput-object v3, v2, v34

    .line 358
    invoke-static {v2}, Lkotlin/collections/MapsKt;->mutableMapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v2

    .line 361
    move-object v3, v0

    check-cast v3, Ljava/lang/CharSequence;

    invoke-interface {v3}, Ljava/lang/CharSequence;->length()I

    move-result v3

    if-lez v3, :cond_8e3

    .line 362
    const-string v3, "avatar"

    invoke-interface {v2, v3, v0}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 365
    :cond_8e3
    invoke-direct {v1, v2}, Lcom/example/data/network/LocalMovieBoxServer;->toJsonString(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    .line 366
    const-string v2, "/wefeed-mobile-bff/user-api/modify"

    invoke-static {}, Lkotlin/collections/MapsKt;->emptyMap()Ljava/util/Map;

    move-result-object v3

    invoke-direct {v1, v14, v2, v3, v0}, Lcom/example/data/network/LocalMovieBoxServer;->makeOfficialRequest(Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 367
    new-instance v1, Lkotlin/Pair;

    const/16 v2, 0xc8

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-direct {v1, v2, v0}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V
    :try_end_8fc
    .catch Ljava/lang/Exception; {:try_start_8a3 .. :try_end_8fc} :catch_8fe

    goto/16 :goto_2c39

    :catch_8fe
    move-exception v0

    .line 369
    :try_start_8ff
    new-instance v1, Lkotlin/Pair;

    const/16 v2, 0x1f4

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v0

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3, v6}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-direct {v1, v2, v0}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V

    goto/16 :goto_2c39

    :cond_921
    const/16 v2, 0xc8

    .line 373
    const-string v3, "/wefeed-mobile-bff/upload/sts-token/v2"

    invoke-static {v8, v3}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v3
    :try_end_929
    .catch Ljava/lang/Exception; {:try_start_8ff .. :try_end_929} :catch_2bfa

    if-eqz v3, :cond_972

    move v3, v2

    .line 375
    :try_start_92c
    const-string v2, "GET"

    move v11, v3

    const-string v3, "/wefeed-mobile-bff/upload/sts-token/v2"
    :try_end_931
    .catch Ljava/lang/Exception; {:try_start_92c .. :try_end_931} :catch_94d

    const/16 v6, 0xc

    const/4 v7, 0x0

    const/4 v4, 0x0

    const/4 v5, 0x0

    move-object/from16 v47, v18

    move/from16 v18, v11

    move-object/from16 v11, v47

    :try_start_93c
    invoke-static/range {v1 .. v7}, Lcom/example/data/network/LocalMovieBoxServer;->makeOfficialRequest$default(Lcom/example/data/network/LocalMovieBoxServer;Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    .line 376
    new-instance v1, Lkotlin/Pair;

    invoke-static/range {v18 .. v18}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-direct {v1, v2, v0}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V
    :try_end_949
    .catch Ljava/lang/Exception; {:try_start_93c .. :try_end_949} :catch_94b

    goto/16 :goto_2c39

    :catch_94b
    move-exception v0

    goto :goto_950

    :catch_94d
    move-exception v0

    move-object/from16 v11, v18

    .line 378
    :goto_950
    :try_start_950
    new-instance v1, Lkotlin/Pair;

    const/16 v2, 0x1f4

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v0

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3, v11}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-direct {v1, v2, v0}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V

    goto/16 :goto_2c39

    :cond_972
    move/from16 v18, v2

    .line 382
    const-string v2, "/upload-avatar"

    invoke-static {v8, v2}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v2
    :try_end_97a
    .catch Ljava/lang/Exception; {:try_start_950 .. :try_end_97a} :catch_2bfa

    if-eqz v2, :cond_a22

    .line 384
    :try_start_97c
    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->parseJsonToMap(Ljava/lang/String;)Ljava/util/Map;

    move-result-object v0

    .line 385
    const-string v2, "image"

    invoke-interface {v0, v2}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    if-eqz v0, :cond_98e

    invoke-virtual {v0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v0

    if-nez v0, :cond_990

    :cond_98e
    move-object/from16 v0, v26

    .line 386
    :cond_990
    move-object v2, v0

    check-cast v2, Ljava/lang/CharSequence;

    invoke-interface {v2}, Ljava/lang/CharSequence;->length()I

    move-result v2

    if-lez v2, :cond_9f0

    const/4 v2, 0x2

    .line 387
    invoke-static {v0, v2}, Landroid/util/Base64;->decode(Ljava/lang/String;I)[B

    move-result-object v0

    .line 388
    new-instance v2, Ljava/io/File;

    iget-object v3, v1, Lcom/example/data/network/LocalMovieBoxServer;->context:Landroid/content/Context;

    invoke-virtual {v3}, Landroid/content/Context;->getFilesDir()Ljava/io/File;

    move-result-object v3

    const-string v5, "avatar.jpg"

    invoke-direct {v2, v3, v5}, Ljava/io/File;-><init>(Ljava/io/File;Ljava/lang/String;)V

    new-instance v3, Ljava/io/FileOutputStream;

    .line 389
    invoke-direct {v3, v2}, Ljava/io/FileOutputStream;-><init>(Ljava/io/File;)V

    check-cast v3, Ljava/io/Closeable;
    :try_end_9b2
    .catch Ljava/lang/Exception; {:try_start_97c .. :try_end_9b2} :catch_9ff

    :try_start_9b2
    move-object v2, v3

    check-cast v2, Ljava/io/FileOutputStream;

    .line 390
    invoke-virtual {v2, v0}, Ljava/io/FileOutputStream;->write([B)V

    .line 391
    sget-object v0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;
    :try_end_9ba
    .catchall {:try_start_9b2 .. :try_end_9ba} :catchall_9e8

    const/4 v2, 0x0

    .line 389
    :try_start_9bb
    invoke-static {v3, v2}, Lkotlin/io/CloseableKt;->closeFinally(Ljava/io/Closeable;Ljava/lang/Throwable;)V

    .line 392
    new-instance v0, Lkotlin/Pair;

    invoke-static/range {v18 .. v18}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    iget-object v1, v1, Lcom/example/data/network/LocalMovieBoxServer;->context:Landroid/content/Context;

    invoke-virtual {v1}, Landroid/content/Context;->getFilesDir()Ljava/io/File;

    move-result-object v1

    invoke-virtual {v1}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v1

    new-instance v3, Ljava/lang/StringBuilder;

    move-object/from16 v5, v17

    invoke-direct {v3, v5}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v3, "/avatar.jpg\"}"

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, v2, v1}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V
    :try_end_9e6
    .catch Ljava/lang/Exception; {:try_start_9bb .. :try_end_9e6} :catch_9ff

    goto/16 :goto_2c3a

    :catchall_9e8
    move-exception v0

    move-object v1, v0

    .line 389
    :try_start_9ea
    throw v1
    :try_end_9eb
    .catchall {:try_start_9ea .. :try_end_9eb} :catchall_9eb

    :catchall_9eb
    move-exception v0

    :try_start_9ec
    invoke-static {v3, v1}, Lkotlin/io/CloseableKt;->closeFinally(Ljava/io/Closeable;Ljava/lang/Throwable;)V

    throw v0

    .line 394
    :cond_9f0
    new-instance v0, Lkotlin/Pair;

    const/16 v1, 0x190

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    const-string v2, "{\"status\": \"error\", \"message\": \"Image data is empty\"}"

    invoke-direct {v0, v1, v2}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V
    :try_end_9fd
    .catch Ljava/lang/Exception; {:try_start_9ec .. :try_end_9fd} :catch_9ff

    goto/16 :goto_2c3a

    :catch_9ff
    move-exception v0

    .line 397
    :try_start_a00
    new-instance v1, Lkotlin/Pair;

    const/16 v2, 0x1f4

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v0

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-direct {v1, v2, v0}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V

    goto/16 :goto_2c39

    :cond_a22
    const/4 v2, 0x0

    .line 401
    const-string v3, "/uploads/avatar.jpg"

    invoke-static {v8, v3}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v3
    :try_end_a29
    .catch Ljava/lang/Exception; {:try_start_a00 .. :try_end_a29} :catch_2bfa

    if-eqz v3, :cond_a79

    .line 403
    :try_start_a2b
    new-instance v0, Ljava/io/File;

    iget-object v1, v1, Lcom/example/data/network/LocalMovieBoxServer;->context:Landroid/content/Context;

    invoke-virtual {v1}, Landroid/content/Context;->getFilesDir()Ljava/io/File;

    move-result-object v1

    const-string v2, "avatar.jpg"

    invoke-direct {v0, v1, v2}, Ljava/io/File;-><init>(Ljava/io/File;Ljava/lang/String;)V

    .line 404
    invoke-virtual {v0}, Ljava/io/File;->exists()Z

    move-result v1

    if-eqz v1, :cond_a54

    .line 405
    invoke-static {v0}, Lkotlin/io/FilesKt;->readBytes(Ljava/io/File;)[B

    move-result-object v0

    new-instance v1, Ljava/lang/String;

    .line 406
    sget-object v2, Lkotlin/text/Charsets;->ISO_8859_1:Ljava/nio/charset/Charset;

    invoke-direct {v1, v0, v2}, Ljava/lang/String;-><init>([BLjava/nio/charset/Charset;)V

    .line 407
    new-instance v0, Lkotlin/Pair;

    invoke-static/range {v18 .. v18}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-direct {v0, v2, v1}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V

    goto/16 :goto_2c3a

    .line 409
    :cond_a54
    new-instance v0, Lkotlin/Pair;

    const/16 v1, 0x194

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    const-string v2, "Not Found"

    invoke-direct {v0, v1, v2}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V
    :try_end_a61
    .catch Ljava/lang/Exception; {:try_start_a2b .. :try_end_a61} :catch_a63

    goto/16 :goto_2c3a

    :catch_a63
    move-exception v0

    .line 412
    :try_start_a64
    new-instance v1, Lkotlin/Pair;

    const/16 v2, 0x1f4

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v0

    if-nez v0, :cond_a74

    const-string v0, "Error"

    :cond_a74
    invoke-direct {v1, v2, v0}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V

    goto/16 :goto_2c39

    .line 416
    :cond_a79
    const-string v3, "/wefeed-mobile-bff/subject-api/want-to-see"

    invoke-static {v8, v3}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v3
    :try_end_a7f
    .catch Ljava/lang/Exception; {:try_start_a64 .. :try_end_a7f} :catch_2bfa

    if-eqz v3, :cond_ab9

    .line 418
    :try_start_a81
    const-string v2, "/wefeed-mobile-bff/subject-api/want-to-see"

    invoke-static {}, Lkotlin/collections/MapsKt;->emptyMap()Ljava/util/Map;

    move-result-object v3

    invoke-direct {v1, v14, v2, v3, v0}, Lcom/example/data/network/LocalMovieBoxServer;->makeOfficialRequest(Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 419
    new-instance v1, Lkotlin/Pair;

    invoke-static/range {v18 .. v18}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-direct {v1, v2, v0}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V
    :try_end_a94
    .catch Ljava/lang/Exception; {:try_start_a81 .. :try_end_a94} :catch_a96

    goto/16 :goto_2c39

    :catch_a96
    move-exception v0

    .line 421
    :try_start_a97
    new-instance v1, Lkotlin/Pair;

    const/16 v2, 0x1f4

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v0

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3, v12}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-direct {v1, v2, v0}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V

    goto/16 :goto_2c39

    .line 425
    :cond_ab9
    const-string v3, "/wefeed-mobile-bff/subject-api/see-list-v2"

    invoke-static {v8, v3}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v3
    :try_end_abf
    .catch Ljava/lang/Exception; {:try_start_a97 .. :try_end_abf} :catch_2bfa

    const-string v12, "1"

    const-string v4, "page"

    if-eqz v3, :cond_b46

    .line 427
    :try_start_ac5
    invoke-interface {v13, v4}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/String;

    if-nez v0, :cond_ace

    move-object v0, v12

    .line 428
    :cond_ace
    const-string v2, "pageSize"

    invoke-interface {v13, v2}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/String;

    if-nez v2, :cond_ada

    const-string v2, "20"

    .line 429
    :cond_ada
    const-string v3, "seeType"

    invoke-interface {v13, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/lang/String;

    if-nez v3, :cond_ae5

    goto :goto_ae6

    :cond_ae5
    move-object v12, v3

    :goto_ae6
    move/from16 v3, v24

    .line 431
    new-array v3, v3, [Lkotlin/Pair;

    invoke-static {v4, v0}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    const/16 v34, 0x0

    aput-object v0, v3, v34

    .line 432
    const-string v0, "pageSize"

    invoke-static {v0, v2}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    const/16 v28, 0x1

    aput-object v0, v3, v28

    .line 433
    const-string v0, "seeType"

    invoke-static {v0, v12}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    const/16 v27, 0x2

    aput-object v0, v3, v27

    .line 430
    invoke-static {v3}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v4

    .line 435
    const-string v2, "GET"

    const-string v3, "/wefeed-mobile-bff/subject-api/see-list-v2"

    const/16 v6, 0x8

    const/4 v7, 0x0

    const/4 v5, 0x0

    invoke-static/range {v1 .. v7}, Lcom/example/data/network/LocalMovieBoxServer;->makeOfficialRequest$default(Lcom/example/data/network/LocalMovieBoxServer;Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    .line 436
    new-instance v1, Lkotlin/Pair;

    invoke-static/range {v18 .. v18}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-direct {v1, v2, v0}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V
    :try_end_b1f
    .catch Ljava/lang/Exception; {:try_start_ac5 .. :try_end_b1f} :catch_b21

    goto/16 :goto_2c39

    :catch_b21
    move-exception v0

    .line 438
    :try_start_b22
    new-instance v1, Lkotlin/Pair;

    const/16 v2, 0x1f4

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v0

    new-instance v3, Ljava/lang/StringBuilder;

    move-object/from16 v4, v25

    invoke-direct {v3, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-direct {v1, v2, v0}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V

    goto/16 :goto_2c39

    .line 441
    :cond_b46
    const-string v3, "/detail-rec"

    invoke-static {v8, v3}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v3
    :try_end_b4c
    .catch Ljava/lang/Exception; {:try_start_b22 .. :try_end_b4c} :catch_2bfa

    const-string v5, "subjectId"

    if-eqz v3, :cond_baa

    .line 443
    :try_start_b50
    invoke-interface {v13, v5}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    move-object v4, v0

    check-cast v4, Ljava/lang/String;

    if-nez v4, :cond_b5b

    move-object/from16 v4, v26

    .line 444
    :cond_b5b
    new-instance v0, Ljava/lang/StringBuilder;

    move-object/from16 v2, v20

    invoke-direct {v0, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v0, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v2, "\",\"page\":1,\"pageSize\":10}"

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 445
    const-string v2, "/wefeed-mobile-bff/subject-api/detail-rec"

    invoke-static {}, Lkotlin/collections/MapsKt;->emptyMap()Ljava/util/Map;

    move-result-object v3

    invoke-direct {v1, v14, v2, v3, v0}, Lcom/example/data/network/LocalMovieBoxServer;->makeOfficialRequest(Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 446
    new-instance v1, Lkotlin/Pair;

    invoke-static/range {v18 .. v18}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-direct {v1, v2, v0}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V
    :try_end_b83
    .catch Ljava/lang/Exception; {:try_start_b50 .. :try_end_b83} :catch_b85

    goto/16 :goto_2c39

    :catch_b85
    move-exception v0

    .line 448
    :try_start_b86
    new-instance v1, Lkotlin/Pair;

    const/16 v2, 0x1f4

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v0

    new-instance v3, Ljava/lang/StringBuilder;

    move-object/from16 v4, v19

    invoke-direct {v3, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-direct {v1, v2, v0}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V

    goto/16 :goto_2c39

    .line 452
    :cond_baa
    const-string v3, "/trending"

    invoke-static {v8, v3}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v3
    :try_end_bb0
    .catch Ljava/lang/Exception; {:try_start_b86 .. :try_end_bb0} :catch_2bfa

    const-string v6, "items"

    const-string v7, "list"

    if-eqz v3, :cond_ca7

    .line 454
    :try_start_bb6
    const-string v0, "/wefeed-mobile-bff/subject-api/trending/v2"

    invoke-static {}, Lkotlin/collections/MapsKt;->emptyMap()Ljava/util/Map;

    move-result-object v3

    const-string v4, "{}"

    invoke-direct {v1, v14, v0, v3, v4}, Lcom/example/data/network/LocalMovieBoxServer;->makeOfficialRequest(Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 455
    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->parseJsonToMap(Ljava/lang/String;)Ljava/util/Map;

    move-result-object v0

    .line 456
    invoke-interface {v0, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    instance-of v4, v3, Ljava/util/Map;

    if-eqz v4, :cond_bd2

    move-object v5, v3

    check-cast v5, Ljava/util/Map;

    goto :goto_bd3

    :cond_bd2
    move-object v5, v2

    :goto_bd3
    if-eqz v5, :cond_bdb

    .line 457
    invoke-interface {v5, v7}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    if-nez v3, :cond_bf0

    :cond_bdb
    if-eqz v5, :cond_be2

    invoke-interface {v5, v6}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    goto :goto_be3

    :cond_be2
    move-object v3, v2

    :goto_be3
    if-nez v3, :cond_bf0

    if-eqz v5, :cond_bee

    const-string v3, "subjects"

    invoke-interface {v5, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    goto :goto_bf1

    :cond_bee
    move-object v5, v2

    goto :goto_bf1

    :cond_bf0
    move-object v5, v3

    :goto_bf1
    nop

    instance-of v3, v5, Ljava/util/List;

    if-eqz v3, :cond_bf9

    check-cast v5, Ljava/util/List;

    goto :goto_bfa

    :cond_bf9
    move-object v5, v2

    :goto_bfa
    if-nez v5, :cond_c0f

    .line 458
    invoke-interface {v0, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    instance-of v3, v0, Ljava/util/List;

    if-eqz v3, :cond_c08

    move-object v5, v0

    check-cast v5, Ljava/util/List;

    goto :goto_c09

    :cond_c08
    move-object v5, v2

    :goto_c09
    if-nez v5, :cond_c0f

    .line 459
    invoke-static {}, Lkotlin/collections/CollectionsKt;->emptyList()Ljava/util/List;

    move-result-object v5

    .line 460
    :cond_c0f
    check-cast v5, Ljava/lang/Iterable;

    .line 1789
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    check-cast v0, Ljava/util/Collection;

    .line 1798
    invoke-interface {v5}, Ljava/lang/Iterable;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :cond_c1c
    :goto_c1c
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_c32

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    .line 1797
    check-cast v3, Ljava/util/Map;

    .line 460
    invoke-direct {v1, v3}, Lcom/example/data/network/LocalMovieBoxServer;->mapItem(Ljava/util/Map;)Ljava/util/Map;

    move-result-object v3

    if-eqz v3, :cond_c1c

    .line 1797
    invoke-interface {v0, v3}, Ljava/util/Collection;->add(Ljava/lang/Object;)Z

    goto :goto_c1c

    .line 1801
    :cond_c32
    check-cast v0, Ljava/util/List;

    const/4 v2, 0x2

    .line 462
    new-array v3, v2, [Lkotlin/Pair;

    const/16 v34, 0x0

    invoke-static/range {v34 .. v34}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-static {v15, v2}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    aput-object v2, v3, v34

    .line 463
    invoke-static {v11, v0}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    const/16 v28, 0x1

    aput-object v0, v3, v28

    .line 461
    invoke-static {v3}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v0

    .line 465
    new-instance v2, Lkotlin/Pair;

    invoke-static/range {v18 .. v18}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->toJsonString(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    invoke-direct {v2, v3, v0}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V
    :try_end_c5c
    .catch Ljava/lang/Exception; {:try_start_bb6 .. :try_end_c5c} :catch_c5d

    goto :goto_ca4

    :catch_c5d
    move-exception v0

    .line 467
    :try_start_c5e
    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v0

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Trending error: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v10, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    const/4 v2, 0x2

    .line 469
    new-array v0, v2, [Lkotlin/Pair;

    const/16 v34, 0x0

    invoke-static/range {v34 .. v34}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-static {v15, v2}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    aput-object v2, v0, v34

    .line 470
    invoke-static {}, Lkotlin/collections/CollectionsKt;->emptyList()Ljava/util/List;

    move-result-object v2

    invoke-static {v11, v2}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    const/16 v28, 0x1

    aput-object v2, v0, v28

    .line 468
    invoke-static {v0}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v0

    .line 472
    new-instance v2, Lkotlin/Pair;

    invoke-static/range {v18 .. v18}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->toJsonString(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    invoke-direct {v2, v3, v0}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V

    :goto_ca4
    move-object v0, v2

    goto/16 :goto_2c3a

    .line 476
    :cond_ca7
    const-string v3, "/discovery"

    invoke-static {v8, v3}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v3
    :try_end_cad
    .catch Ljava/lang/Exception; {:try_start_c5e .. :try_end_cad} :catch_2bfa

    if-eqz v3, :cond_da0

    .line 478
    :try_start_caf
    const-string v0, "/wefeed-mobile-bff/subject-api/top-rec"

    invoke-static {}, Lkotlin/collections/MapsKt;->emptyMap()Ljava/util/Map;

    move-result-object v3

    const-string v4, "{}"

    invoke-direct {v1, v14, v0, v3, v4}, Lcom/example/data/network/LocalMovieBoxServer;->makeOfficialRequest(Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 479
    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->parseJsonToMap(Ljava/lang/String;)Ljava/util/Map;

    move-result-object v0

    .line 480
    invoke-interface {v0, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    instance-of v4, v3, Ljava/util/Map;

    if-eqz v4, :cond_ccb

    move-object v5, v3

    check-cast v5, Ljava/util/Map;

    goto :goto_ccc

    :cond_ccb
    move-object v5, v2

    :goto_ccc
    if-eqz v5, :cond_cd4

    .line 481
    invoke-interface {v5, v7}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    if-nez v3, :cond_ce9

    :cond_cd4
    if-eqz v5, :cond_cdb

    invoke-interface {v5, v6}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    goto :goto_cdc

    :cond_cdb
    move-object v3, v2

    :goto_cdc
    if-nez v3, :cond_ce9

    if-eqz v5, :cond_ce7

    const-string v3, "subjects"

    invoke-interface {v5, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    goto :goto_cea

    :cond_ce7
    move-object v5, v2

    goto :goto_cea

    :cond_ce9
    move-object v5, v3

    :goto_cea
    nop

    instance-of v3, v5, Ljava/util/List;

    if-eqz v3, :cond_cf2

    check-cast v5, Ljava/util/List;

    goto :goto_cf3

    :cond_cf2
    move-object v5, v2

    :goto_cf3
    if-nez v5, :cond_d08

    .line 482
    invoke-interface {v0, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    instance-of v3, v0, Ljava/util/List;

    if-eqz v3, :cond_d01

    move-object v5, v0

    check-cast v5, Ljava/util/List;

    goto :goto_d02

    :cond_d01
    move-object v5, v2

    :goto_d02
    if-nez v5, :cond_d08

    .line 483
    invoke-static {}, Lkotlin/collections/CollectionsKt;->emptyList()Ljava/util/List;

    move-result-object v5

    .line 484
    :cond_d08
    check-cast v5, Ljava/lang/Iterable;

    .line 1802
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    check-cast v0, Ljava/util/Collection;

    .line 1811
    invoke-interface {v5}, Ljava/lang/Iterable;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :cond_d15
    :goto_d15
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_d2b

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    .line 1810
    check-cast v3, Ljava/util/Map;

    .line 484
    invoke-direct {v1, v3}, Lcom/example/data/network/LocalMovieBoxServer;->mapItem(Ljava/util/Map;)Ljava/util/Map;

    move-result-object v3

    if-eqz v3, :cond_d15

    .line 1810
    invoke-interface {v0, v3}, Ljava/util/Collection;->add(Ljava/lang/Object;)Z

    goto :goto_d15

    .line 1814
    :cond_d2b
    check-cast v0, Ljava/util/List;

    const/4 v2, 0x2

    .line 486
    new-array v3, v2, [Lkotlin/Pair;

    const/16 v34, 0x0

    invoke-static/range {v34 .. v34}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-static {v15, v2}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    aput-object v2, v3, v34

    .line 487
    invoke-static {v11, v0}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    const/16 v28, 0x1

    aput-object v0, v3, v28

    .line 485
    invoke-static {v3}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v0

    .line 489
    new-instance v2, Lkotlin/Pair;

    invoke-static/range {v18 .. v18}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->toJsonString(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    invoke-direct {v2, v3, v0}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V
    :try_end_d55
    .catch Ljava/lang/Exception; {:try_start_caf .. :try_end_d55} :catch_d57

    goto/16 :goto_ca4

    :catch_d57
    move-exception v0

    .line 491
    :try_start_d58
    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v0

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Discovery error: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v10, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    const/4 v2, 0x2

    .line 493
    new-array v0, v2, [Lkotlin/Pair;

    const/16 v34, 0x0

    invoke-static/range {v34 .. v34}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-static {v15, v2}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    aput-object v2, v0, v34

    .line 494
    invoke-static {}, Lkotlin/collections/CollectionsKt;->emptyList()Ljava/util/List;

    move-result-object v2

    invoke-static {v11, v2}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    const/16 v28, 0x1

    aput-object v2, v0, v28

    .line 492
    invoke-static {v0}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v0

    .line 496
    new-instance v2, Lkotlin/Pair;

    invoke-static/range {v18 .. v18}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->toJsonString(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    invoke-direct {v2, v3, v0}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V

    goto/16 :goto_ca4

    .line 500
    :cond_da0
    const-string v3, "/search"

    invoke-static {v8, v3}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_ef8

    .line 501
    const-string v0, "q"

    invoke-interface {v13, v0}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/String;

    if-nez v0, :cond_db4

    move-object/from16 v0, v26

    .line 502
    :cond_db4
    invoke-interface {v13, v4}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/lang/String;

    if-eqz v3, :cond_dc7

    invoke-static {v3}, Lkotlin/text/StringsKt;->toIntOrNull(Ljava/lang/String;)Ljava/lang/Integer;

    move-result-object v3

    if-eqz v3, :cond_dc7

    invoke-virtual {v3}, Ljava/lang/Integer;->intValue()I

    move-result v3
    :try_end_dc6
    .catch Ljava/lang/Exception; {:try_start_d58 .. :try_end_dc6} :catch_2bfa

    goto :goto_dc8

    :cond_dc7
    const/4 v3, 0x1

    :goto_dc8
    const/4 v5, 0x3

    .line 505
    :try_start_dc9
    new-array v5, v5, [Lkotlin/Pair;

    const-string v12, "keyword"

    invoke-static {v12, v0}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    const/16 v34, 0x0

    aput-object v0, v5, v34

    .line 506
    invoke-static {v3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v0

    invoke-static {v4, v0}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    const/16 v28, 0x1

    aput-object v0, v5, v28

    .line 507
    const-string v0, "pageSize"

    const/16 v3, 0x14

    invoke-static {v3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    invoke-static {v0, v3}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    const/16 v27, 0x2

    aput-object v0, v5, v27

    .line 504
    invoke-static {v5}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v0

    .line 509
    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->toJsonString(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    .line 510
    const-string v3, "/wefeed-mobile-bff/subject-api/search"

    invoke-static {}, Lkotlin/collections/MapsKt;->emptyMap()Ljava/util/Map;

    move-result-object v4

    invoke-direct {v1, v14, v3, v4, v0}, Lcom/example/data/network/LocalMovieBoxServer;->makeOfficialRequest(Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 511
    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->parseJsonToMap(Ljava/lang/String;)Ljava/util/Map;

    move-result-object v0

    .line 512
    invoke-interface {v0, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    instance-of v4, v3, Ljava/util/Map;

    if-eqz v4, :cond_e13

    move-object v5, v3

    check-cast v5, Ljava/util/Map;

    goto :goto_e14

    :cond_e13
    move-object v5, v2

    :goto_e14
    if-eqz v5, :cond_e1c

    .line 513
    invoke-interface {v5, v7}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    if-nez v3, :cond_e31

    :cond_e1c
    if-eqz v5, :cond_e23

    invoke-interface {v5, v6}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    goto :goto_e24

    :cond_e23
    move-object v3, v2

    :goto_e24
    if-nez v3, :cond_e31

    if-eqz v5, :cond_e2f

    const-string v3, "subjects"

    invoke-interface {v5, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    goto :goto_e32

    :cond_e2f
    move-object v5, v2

    goto :goto_e32

    :cond_e31
    move-object v5, v3

    :goto_e32
    nop

    instance-of v3, v5, Ljava/util/List;

    if-eqz v3, :cond_e3a

    check-cast v5, Ljava/util/List;

    goto :goto_e3b

    :cond_e3a
    move-object v5, v2

    :goto_e3b
    if-nez v5, :cond_e50

    .line 514
    invoke-interface {v0, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    instance-of v3, v0, Ljava/util/List;

    if-eqz v3, :cond_e49

    move-object v5, v0

    check-cast v5, Ljava/util/List;

    goto :goto_e4a

    :cond_e49
    move-object v5, v2

    :goto_e4a
    if-nez v5, :cond_e50

    .line 515
    invoke-static {}, Lkotlin/collections/CollectionsKt;->emptyList()Ljava/util/List;

    move-result-object v5

    .line 516
    :cond_e50
    check-cast v5, Ljava/lang/Iterable;

    .line 1815
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    check-cast v0, Ljava/util/Collection;

    .line 1824
    invoke-interface {v5}, Ljava/lang/Iterable;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :cond_e5d
    :goto_e5d
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_e73

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    .line 1823
    check-cast v3, Ljava/util/Map;

    .line 516
    invoke-direct {v1, v3}, Lcom/example/data/network/LocalMovieBoxServer;->mapItem(Ljava/util/Map;)Ljava/util/Map;

    move-result-object v3

    if-eqz v3, :cond_e5d

    .line 1823
    invoke-interface {v0, v3}, Ljava/util/Collection;->add(Ljava/lang/Object;)Z

    goto :goto_e5d

    .line 1827
    :cond_e73
    check-cast v0, Ljava/util/List;

    const/4 v2, 0x2

    .line 518
    new-array v3, v2, [Lkotlin/Pair;

    const/16 v34, 0x0

    invoke-static/range {v34 .. v34}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-static {v15, v2}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    aput-object v2, v3, v34

    .line 519
    invoke-static {v6, v0}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    invoke-static {v0}, Lkotlin/collections/MapsKt;->mapOf(Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v0

    invoke-static {v11, v0}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    const/16 v28, 0x1

    aput-object v0, v3, v28

    .line 517
    invoke-static {v3}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v0

    .line 521
    new-instance v2, Lkotlin/Pair;

    invoke-static/range {v18 .. v18}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->toJsonString(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    invoke-direct {v2, v3, v0}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V
    :try_end_ea5
    .catch Ljava/lang/Exception; {:try_start_dc9 .. :try_end_ea5} :catch_ea7

    goto/16 :goto_ca4

    :catch_ea7
    move-exception v0

    .line 523
    :try_start_ea8
    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v0

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Search error: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v10, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    const/4 v2, 0x2

    .line 525
    new-array v0, v2, [Lkotlin/Pair;

    const/16 v34, 0x0

    invoke-static/range {v34 .. v34}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-static {v15, v2}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    aput-object v2, v0, v34

    .line 526
    invoke-static {}, Lkotlin/collections/CollectionsKt;->emptyList()Ljava/util/List;

    move-result-object v2

    invoke-static {v6, v2}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    invoke-static {v2}, Lkotlin/collections/MapsKt;->mapOf(Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v2

    invoke-static {v11, v2}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    const/16 v28, 0x1

    aput-object v2, v0, v28

    .line 524
    invoke-static {v0}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v0

    .line 528
    new-instance v2, Lkotlin/Pair;

    invoke-static/range {v18 .. v18}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->toJsonString(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    invoke-direct {v2, v3, v0}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V

    goto/16 :goto_ca4

    :cond_ef8
    const/16 v28, 0x1

    .line 532
    const-string v3, "/wefeed-mobile-bff/subject-api/trending/v2"

    invoke-static {v8, v3}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v3
    :try_end_f00
    .catch Ljava/lang/Exception; {:try_start_ea8 .. :try_end_f00} :catch_2bfa

    move-object/from16 p3, v6

    const-string v6, "title"

    move-object/from16 v17, v6

    const-string v6, "name"

    if-nez v3, :cond_27aa

    :try_start_f0a
    const-string v3, "/search-suggestions"

    invoke-static {v8, v3}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_f14

    goto/16 :goto_27aa

    .line 616
    :cond_f14
    const-string v3, "/detail/"

    const/4 v1, 0x0

    const/4 v14, 0x2

    invoke-static {v8, v3, v1, v14, v2}, Lkotlin/text/StringsKt;->startsWith$default(Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Z

    move-result v3
    :try_end_f1c
    .catch Ljava/lang/Exception; {:try_start_f0a .. :try_end_f1c} :catch_27a3

    if-eqz v3, :cond_1475

    .line 617
    :try_start_f1e
    const-string v0, "/detail/"

    invoke-static {v8, v0, v2, v14, v2}, Lkotlin/text/StringsKt;->substringAfter$default(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    const-string v3, "?"

    invoke-static {v0, v3, v2, v14, v2}, Lkotlin/text/StringsKt;->substringBefore$default(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    const-string v3, "/"

    invoke-static {v0, v3, v2, v14, v2}, Lkotlin/text/StringsKt;->substringBefore$default(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;

    move-result-object v12
    :try_end_f30
    .catch Ljava/lang/Exception; {:try_start_f1e .. :try_end_f30} :catch_2bfa

    move-object v13, v2

    .line 619
    :try_start_f31
    const-string v2, "GET"

    const-string v3, "/wefeed-mobile-bff/subject-api/get"

    invoke-static {v5, v12}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    invoke-static {v0}, Lkotlin/collections/MapsKt;->mapOf(Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v4
    :try_end_f3d
    .catch Ljava/lang/Exception; {:try_start_f31 .. :try_end_f3d} :catch_1326

    move-object v7, v6

    const/16 v6, 0x8

    move-object/from16 v16, v7

    const/4 v7, 0x0

    move-object/from16 v17, v5

    const/4 v5, 0x0

    move-object/from16 v19, v9

    move/from16 v27, v14

    move-object/from16 v13, v16

    move-object/from16 v14, v17

    move v9, v1

    move-object/from16 v1, p0

    :try_start_f51
    invoke-static/range {v1 .. v7}, Lcom/example/data/network/LocalMovieBoxServer;->makeOfficialRequest$default(Lcom/example/data/network/LocalMovieBoxServer;Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    .line 620
    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->parseJsonToMap(Ljava/lang/String;)Ljava/util/Map;

    move-result-object v0

    .line 621
    invoke-interface {v0, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    instance-of v2, v0, Ljava/util/Map;

    if-eqz v2, :cond_f66

    move-object v5, v0

    check-cast v5, Ljava/util/Map;

    move-object v2, v5

    goto :goto_f67

    :cond_f66
    const/4 v2, 0x0

    :goto_f67
    if-nez v2, :cond_f77

    new-instance v0, Lkotlin/Pair;

    const/16 v2, 0x194

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    const-string v3, "{\"code\": 1, \"msg\": \"Not found\"}"

    invoke-direct {v0, v2, v3}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V

    return-object v0

    .line 624
    :cond_f77
    const-string v0, "dubs"

    invoke-interface {v2, v0}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    instance-of v3, v0, Ljava/util/List;

    if-eqz v3, :cond_f85

    move-object v5, v0

    check-cast v5, Ljava/util/List;

    goto :goto_f86

    :cond_f85
    const/4 v5, 0x0

    :goto_f86
    if-nez v5, :cond_f8c

    invoke-static {}, Lkotlin/collections/CollectionsKt;->emptyList()Ljava/util/List;

    move-result-object v5

    .line 625
    :cond_f8c
    check-cast v5, Ljava/lang/Iterable;

    invoke-interface {v5}, Ljava/lang/Iterable;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_f92
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v3
    :try_end_f96
    .catch Ljava/lang/Exception; {:try_start_f51 .. :try_end_f96} :catch_1322

    if-eqz v3, :cond_fdd

    :try_start_f98
    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v5

    move-object v3, v5

    check-cast v3, Ljava/util/Map;

    const-string v4, "original"

    invoke-interface {v3, v4}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    invoke-static/range {v28 .. v28}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v6

    invoke-static {v4, v6}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v4

    if-nez v4, :cond_fcf

    const-string v4, "original"

    invoke-interface {v3, v4}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    if-eqz v3, :cond_fc7

    invoke-virtual {v3}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v3

    if-eqz v3, :cond_fc7

    invoke-static {v3}, Ljava/lang/Boolean;->parseBoolean(Ljava/lang/String;)Z

    move-result v3
    :try_end_fc1
    .catch Ljava/lang/Exception; {:try_start_f98 .. :try_end_fc1} :catch_fd8

    move/from16 v4, v28

    if-ne v3, v4, :cond_fc9

    move v3, v4

    goto :goto_fca

    :cond_fc7
    move/from16 v4, v28

    :cond_fc9
    move v3, v9

    :goto_fca
    if-eqz v3, :cond_fcd

    goto :goto_fd1

    :cond_fcd
    move v3, v9

    goto :goto_fd2

    :cond_fcf
    move/from16 v4, v28

    :goto_fd1
    move v3, v4

    :goto_fd2
    if-eqz v3, :cond_fd5

    goto :goto_fe0

    :cond_fd5
    move/from16 v28, v4

    goto :goto_f92

    :catch_fd8
    move-exception v0

    move/from16 v4, v28

    goto/16 :goto_1323

    :cond_fdd
    move/from16 v4, v28

    const/4 v5, 0x0

    :goto_fe0
    :try_start_fe0
    check-cast v5, Ljava/util/Map;

    if-eqz v5, :cond_fef

    .line 626
    invoke-interface {v5, v14}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    if-eqz v0, :cond_fef

    invoke-virtual {v0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v5

    goto :goto_ff0

    :cond_fef
    const/4 v5, 0x0

    .line 627
    :goto_ff0
    move-object v0, v5

    check-cast v0, Ljava/lang/CharSequence;

    if-eqz v0, :cond_ffe

    invoke-interface {v0}, Ljava/lang/CharSequence;->length()I

    move-result v0

    if-nez v0, :cond_ffc

    goto :goto_ffe

    :cond_ffc
    move v0, v9

    goto :goto_fff

    :cond_ffe
    :goto_ffe
    move v0, v4

    :goto_fff
    if-nez v0, :cond_111a

    invoke-static {v5, v12}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v0
    :try_end_1005
    .catch Ljava/lang/Exception; {:try_start_fe0 .. :try_end_1005} :catch_131e

    if-nez v0, :cond_111a

    move-object v3, v2

    .line 629
    :try_start_1008
    const-string v2, "GET"
    :try_end_100a
    .catch Ljava/lang/Exception; {:try_start_1008 .. :try_end_100a} :catch_10f4

    move-object v6, v3

    :try_start_100b
    const-string v3, "/wefeed-mobile-bff/subject-api/get"

    invoke-static {v14, v5}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    invoke-static {v0}, Lkotlin/collections/MapsKt;->mapOf(Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v0
    :try_end_1015
    .catch Ljava/lang/Exception; {:try_start_100b .. :try_end_1015} :catch_10f2

    move-object v5, v6

    const/16 v6, 0x8

    const/4 v7, 0x0

    move-object/from16 v16, v5

    const/4 v5, 0x0

    move/from16 v34, v9

    move-object/from16 p1, v16

    move v9, v4

    move-object v4, v0

    :try_start_1022
    invoke-static/range {v1 .. v7}, Lcom/example/data/network/LocalMovieBoxServer;->makeOfficialRequest$default(Lcom/example/data/network/LocalMovieBoxServer;Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    .line 630
    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->parseJsonToMap(Ljava/lang/String;)Ljava/util/Map;

    move-result-object v0

    .line 631
    invoke-interface {v0, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    instance-of v2, v0, Ljava/util/Map;
    :try_end_1030
    .catch Ljava/lang/Exception; {:try_start_1022 .. :try_end_1030} :catch_10ec

    if-eqz v2, :cond_103d

    :try_start_1032
    move-object v5, v0

    check-cast v5, Ljava/util/Map;
    :try_end_1035
    .catch Ljava/lang/Exception; {:try_start_1032 .. :try_end_1035} :catch_1036

    goto :goto_103e

    :catch_1036
    move-exception v0

    move-object/from16 v2, p1

    move/from16 v28, v9

    goto/16 :goto_10fb

    :cond_103d
    const/4 v5, 0x0

    :goto_103e
    if-eqz v5, :cond_10e2

    .line 633
    :try_start_1040
    invoke-static/range {p1 .. p1}, Lkotlin/collections/MapsKt;->toMutableMap(Ljava/util/Map;)Ljava/util/Map;

    move-result-object v0

    const/16 v2, 0x9

    .line 634
    new-array v2, v2, [Ljava/lang/String;

    const-string v3, "description"

    aput-object v3, v2, v34

    const-string v3, "duration"

    aput-object v3, v2, v9

    const-string v3, "year"

    aput-object v3, v2, v27

    const-string v3, "imdbRating"

    const/16 v24, 0x3

    aput-object v3, v2, v24

    const-string v3, "genres"

    const/16 v23, 0x4

    aput-object v3, v2, v23

    const-string v3, "staffList"

    const/4 v4, 0x5

    aput-object v3, v2, v4

    const-string v3, "actorList"

    const/4 v4, 0x6

    aput-object v3, v2, v4

    const-string v3, "totalSeasons"

    const/4 v4, 0x7

    aput-object v3, v2, v4

    const-string v3, "totalEpisode"

    const/16 v4, 0x8

    aput-object v3, v2, v4

    invoke-static {v2}, Lkotlin/collections/CollectionsKt;->listOf([Ljava/lang/Object;)Ljava/util/List;

    move-result-object v2

    check-cast v2, Ljava/lang/Iterable;

    .line 1881
    invoke-interface {v2}, Ljava/lang/Iterable;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :goto_107f
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_10e0

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/lang/String;

    .line 635
    invoke-interface {v5, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4
    :try_end_108f
    .catch Ljava/lang/Exception; {:try_start_1040 .. :try_end_108f} :catch_10ec

    move-object/from16 v6, p1

    .line 636
    :try_start_1091
    invoke-interface {v6, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v7

    if-eqz v4, :cond_10d3

    if-eqz v7, :cond_10c9

    .line 637
    invoke-virtual {v7}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v16

    check-cast v16, Ljava/lang/CharSequence;

    invoke-interface/range {v16 .. v16}, Ljava/lang/CharSequence;->length()I

    move-result v16
    :try_end_10a3
    .catch Ljava/lang/Exception; {:try_start_1091 .. :try_end_10a3} :catch_10de

    if-nez v16, :cond_10a8

    move/from16 v16, v9

    goto :goto_10aa

    :cond_10a8
    move/from16 v16, v34

    :goto_10aa
    if-nez v16, :cond_10c9

    move/from16 v28, v9

    :try_start_10ae
    invoke-virtual {v7}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v9

    move-object/from16 p1, v2

    const-string v2, "0"

    invoke-static {v9, v2}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v2

    if-nez v2, :cond_10cd

    invoke-virtual {v7}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v2

    const-string v7, "0 min"

    invoke-static {v2, v7}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_10d7

    goto :goto_10cd

    :cond_10c9
    move-object/from16 p1, v2

    move/from16 v28, v9

    .line 638
    :cond_10cd
    :goto_10cd
    invoke-interface {v0, v3, v4}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    :try_end_10d0
    .catch Ljava/lang/Exception; {:try_start_10ae .. :try_end_10d0} :catch_10d1

    goto :goto_10d7

    :catch_10d1
    move-exception v0

    goto :goto_10fa

    :cond_10d3
    move-object/from16 p1, v2

    move/from16 v28, v9

    :cond_10d7
    :goto_10d7
    move-object/from16 v2, p1

    move-object/from16 p1, v6

    move/from16 v9, v28

    goto :goto_107f

    :catch_10de
    move-exception v0

    goto :goto_10ef

    :cond_10e0
    move-object v2, v0

    goto :goto_10e5

    :cond_10e2
    move-object/from16 v6, p1

    move-object v2, v6

    :goto_10e5
    move/from16 v28, v9

    .line 641
    :try_start_10e7
    sget-object v0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;
    :try_end_10e9
    .catch Ljava/lang/Exception; {:try_start_10e7 .. :try_end_10e9} :catch_10ea

    goto :goto_1120

    :catch_10ea
    move-exception v0

    goto :goto_10fb

    :catch_10ec
    move-exception v0

    move-object/from16 v6, p1

    :goto_10ef
    move/from16 v28, v9

    goto :goto_10fa

    :catch_10f2
    move-exception v0

    goto :goto_10f6

    :catch_10f4
    move-exception v0

    move-object v6, v3

    :goto_10f6
    move/from16 v28, v4

    move/from16 v34, v9

    :goto_10fa
    move-object v2, v6

    .line 644
    :goto_10fb
    :try_start_10fb
    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v0

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "Failed to fetch main detail for dub: "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v10, v0}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    goto :goto_1120

    :cond_111a
    move-object v6, v2

    move/from16 v28, v4

    move/from16 v34, v9

    move-object v2, v6

    .line 648
    :goto_1120
    invoke-direct {v1, v2}, Lcom/example/data/network/LocalMovieBoxServer;->mapItem(Ljava/util/Map;)Ljava/util/Map;

    move-result-object v0

    if-nez v0, :cond_1127

    move-object v0, v2

    .line 649
    :cond_1127
    invoke-static {v0}, Lkotlin/collections/MapsKt;->toMutableMap(Ljava/util/Map;)Ljava/util/Map;

    move-result-object v0

    .line 652
    const-string v3, "staffList"

    invoke-interface {v2, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    instance-of v4, v3, Ljava/util/List;

    if-eqz v4, :cond_1139

    move-object v5, v3

    check-cast v5, Ljava/util/List;

    goto :goto_113a

    :cond_1139
    const/4 v5, 0x0

    :goto_113a
    if-nez v5, :cond_1151

    const-string v3, "actorList"

    invoke-interface {v2, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    instance-of v4, v3, Ljava/util/List;

    if-eqz v4, :cond_114a

    move-object v5, v3

    check-cast v5, Ljava/util/List;

    goto :goto_114b

    :cond_114a
    const/4 v5, 0x0

    :goto_114b
    if-nez v5, :cond_1151

    invoke-static {}, Lkotlin/collections/CollectionsKt;->emptyList()Ljava/util/List;

    move-result-object v5

    .line 653
    :cond_1151
    check-cast v5, Ljava/lang/Iterable;

    .line 1883
    new-instance v3, Ljava/util/ArrayList;

    invoke-direct {v3}, Ljava/util/ArrayList;-><init>()V

    check-cast v3, Ljava/util/Collection;

    .line 1892
    invoke-interface {v5}, Ljava/lang/Iterable;->iterator()Ljava/util/Iterator;

    move-result-object v4

    :goto_115e
    invoke-interface {v4}, Ljava/util/Iterator;->hasNext()Z

    move-result v5

    if-eqz v5, :cond_11da

    invoke-interface {v4}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v5

    .line 1891
    check-cast v5, Ljava/util/Map;

    .line 654
    invoke-interface {v5, v13}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    if-nez v6, :cond_117a

    const-string v6, "actorName"

    invoke-interface {v5, v6}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    if-nez v6, :cond_117a

    const-string v6, "Unknown"

    .line 655
    :cond_117a
    const-string v7, "character"

    invoke-interface {v5, v7}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v7

    if-nez v7, :cond_118c

    const-string v7, "role"

    invoke-interface {v5, v7}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v7

    if-nez v7, :cond_118c

    const-string v7, "Cast"

    .line 656
    :cond_118c
    const-string v9, "avatarUrl"

    invoke-interface {v5, v9}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v9

    if-nez v9, :cond_11b3

    const-string v9, "avatar"

    invoke-interface {v5, v9}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v9

    if-nez v9, :cond_11b3

    const-string v9, "photo"

    invoke-interface {v5, v9}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v9

    if-nez v9, :cond_11b3

    const-string v9, "poster"

    invoke-interface {v5, v9}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    move-object/from16 p1, v4

    if-nez v5, :cond_11b1

    move-object/from16 v9, v26

    goto :goto_11b5

    :cond_11b1
    move-object v9, v5

    goto :goto_11b5

    :cond_11b3
    move-object/from16 p1, v4

    :goto_11b5
    const/4 v5, 0x3

    .line 657
    new-array v4, v5, [Lkotlin/Pair;

    invoke-static {v13, v6}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v5

    aput-object v5, v4, v34

    const-string v5, "role"

    invoke-static {v5, v7}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v5

    aput-object v5, v4, v28

    const-string v5, "avatar"

    invoke-static {v5, v9}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v5

    aput-object v5, v4, v27

    invoke-static {v4}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v4

    if-eqz v4, :cond_11d7

    .line 1891
    invoke-interface {v3, v4}, Ljava/util/Collection;->add(Ljava/lang/Object;)Z

    :cond_11d7
    move-object/from16 v4, p1

    goto :goto_115e

    .line 1895
    :cond_11da
    check-cast v3, Ljava/util/List;

    .line 659
    const-string v4, "cast"

    invoke-interface {v0, v4, v3}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 662
    new-instance v3, Ljava/util/ArrayList;

    invoke-direct {v3}, Ljava/util/ArrayList;-><init>()V

    check-cast v3, Ljava/util/List;

    .line 663
    const-string v4, "dubs"

    invoke-interface {v2, v4}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    instance-of v5, v4, Ljava/util/List;

    if-eqz v5, :cond_11f6

    move-object v5, v4

    check-cast v5, Ljava/util/List;

    goto :goto_11f7

    :cond_11f6
    const/4 v5, 0x0

    :goto_11f7
    if-nez v5, :cond_11fd

    invoke-static {}, Lkotlin/collections/CollectionsKt;->emptyList()Ljava/util/List;

    move-result-object v5

    .line 664
    :cond_11fd
    invoke-interface {v5}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v4

    :goto_1201
    invoke-interface {v4}, Ljava/util/Iterator;->hasNext()Z

    move-result v5

    if-eqz v5, :cond_1265

    invoke-interface {v4}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Ljava/util/Map;

    .line 665
    invoke-interface {v5, v14}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    if-eqz v6, :cond_1260

    invoke-virtual {v6}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v6

    if-nez v6, :cond_121a

    goto :goto_1260

    :cond_121a
    const/4 v7, 0x3

    .line 667
    new-array v9, v7, [Lkotlin/Pair;

    const-string v7, "resourceId"

    move-object/from16 p1, v4

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    move-object/from16 v16, v9

    const-string v9, "dub_"

    invoke-virtual {v4, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v7, v4}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v4

    aput-object v4, v16, v34

    .line 668
    const-string v4, "lanName"

    invoke-interface {v5, v4}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    if-nez v4, :cond_1246

    const-string v4, "Custom Dub"

    :cond_1246
    invoke-static {v13, v4}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v4

    aput-object v4, v16, v28

    .line 669
    const-string v4, "totalEpisode"

    invoke-static/range {v34 .. v34}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    invoke-static {v4, v5}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v4

    aput-object v4, v16, v27

    .line 666
    invoke-static/range {v16 .. v16}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v4

    invoke-interface {v3, v4}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    goto :goto_1262

    :cond_1260
    :goto_1260
    move-object/from16 p1, v4

    :goto_1262
    move-object/from16 v4, p1

    goto :goto_1201

    .line 672
    :cond_1265
    const-string v4, "resourceDetectors"

    invoke-interface {v2, v4}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    instance-of v4, v2, Ljava/util/List;

    if-eqz v4, :cond_1273

    move-object v5, v2

    check-cast v5, Ljava/util/List;

    goto :goto_1274

    :cond_1273
    const/4 v5, 0x0

    :goto_1274
    if-nez v5, :cond_127a

    invoke-static {}, Lkotlin/collections/CollectionsKt;->emptyList()Ljava/util/List;

    move-result-object v5

    .line 673
    :cond_127a
    invoke-interface {v5}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :goto_127e
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v4

    if-eqz v4, :cond_12f0

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Ljava/util/Map;

    .line 674
    const-string v5, "resourceId"

    invoke-interface {v4, v5}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    if-eqz v5, :cond_12eb

    invoke-virtual {v5}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v5

    if-nez v5, :cond_1299

    goto :goto_12eb

    :cond_1299
    const/4 v7, 0x3

    .line 676
    new-array v6, v7, [Lkotlin/Pair;

    const-string v7, "resourceId"

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    move-object/from16 p1, v2

    const-string v2, "res_"

    invoke-virtual {v9, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v7, v2}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    aput-object v2, v6, v34

    .line 677
    invoke-interface {v4, v13}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    if-nez v2, :cond_12c9

    const-string v2, "uploadBy"

    invoke-interface {v4, v2}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    if-nez v2, :cond_12c9

    const-string v2, "Resource"

    :cond_12c9
    invoke-static {v13, v2}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    aput-object v2, v6, v28

    .line 678
    const-string v2, "totalEpisode"

    const-string v5, "totalEpisode"

    invoke-interface {v4, v5}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    if-nez v4, :cond_12dd

    invoke-static/range {v34 .. v34}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v4

    :cond_12dd
    invoke-static {v2, v4}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    aput-object v2, v6, v27

    .line 675
    invoke-static {v6}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v2

    invoke-interface {v3, v2}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    goto :goto_12ed

    :cond_12eb
    :goto_12eb
    move-object/from16 p1, v2

    :goto_12ed
    move-object/from16 v2, p1

    goto :goto_127e

    .line 681
    :cond_12f0
    const-string v2, "resourceDetectors"

    invoke-interface {v0, v2, v3}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    move/from16 v2, v27

    .line 684
    new-array v3, v2, [Lkotlin/Pair;

    invoke-static/range {v34 .. v34}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-static {v15, v2}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    aput-object v2, v3, v34

    .line 685
    invoke-static {v11, v0}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    aput-object v0, v3, v28

    .line 683
    invoke-static {v3}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v0

    .line 687
    new-instance v2, Lkotlin/Pair;

    invoke-static/range {v18 .. v18}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->toJsonString(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    invoke-direct {v2, v3, v0}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V
    :try_end_131a
    .catch Ljava/lang/Exception; {:try_start_10fb .. :try_end_131a} :catch_131c

    goto/16 :goto_ca4

    :catch_131c
    move-exception v0

    goto :goto_132f

    :catch_131e
    move-exception v0

    move/from16 v28, v4

    goto :goto_1323

    :catch_1322
    move-exception v0

    :goto_1323
    move/from16 v34, v9

    goto :goto_132f

    :catch_1326
    move-exception v0

    move/from16 v34, v1

    move-object v14, v5

    move-object v13, v6

    move-object/from16 v19, v9

    move-object/from16 v1, p0

    .line 689
    :goto_132f
    :try_start_132f
    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v0

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Detail fallback triggered: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v10, v0}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 690
    invoke-direct {v1}, Lcom/example/data/network/LocalMovieBoxServer;->getMockTrending()Ljava/util/List;

    move-result-object v0

    check-cast v0, Ljava/lang/Iterable;

    invoke-interface {v0}, Ljava/lang/Iterable;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :cond_1353
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    if-eqz v2, :cond_136b

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v5

    move-object v2, v5

    check-cast v2, Ljava/util/Map;

    invoke-interface {v2, v14}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    invoke-static {v2, v12}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_1353

    goto :goto_136c

    :cond_136b
    const/4 v5, 0x0

    :goto_136c
    check-cast v5, Ljava/util/Map;

    if-nez v5, :cond_137d

    invoke-direct {v1}, Lcom/example/data/network/LocalMovieBoxServer;->getMockTrending()Ljava/util/List;

    move-result-object v0

    move/from16 v9, v34

    invoke-interface {v0, v9}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    move-object v5, v0

    check-cast v5, Ljava/util/Map;

    .line 691
    :cond_137d
    invoke-static {v5}, Lkotlin/collections/MapsKt;->toMutableMap(Ljava/util/Map;)Ljava/util/Map;

    move-result-object v0

    .line 692
    const-string v2, "cast"

    const/4 v3, 0x2

    .line 693
    new-array v4, v3, [Ljava/util/Map;

    const/4 v5, 0x3

    new-array v3, v5, [Lkotlin/Pair;

    const-string v5, "Keanu Reeves"

    invoke-static {v13, v5}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v5

    const/16 v34, 0x0

    aput-object v5, v3, v34

    const-string v5, "role"

    const-string v6, "Neo"

    invoke-static {v5, v6}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v5

    aput-object v5, v3, v28

    const-string v5, "avatar"

    const-string v6, "https://image.tmdb.org/t/p/w500/b9v69EMT2g7uY78MKg6Y67688v6.jpg"

    invoke-static {v5, v6}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v5

    const/16 v27, 0x2

    aput-object v5, v3, v27

    invoke-static {v3}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v3

    const/16 v34, 0x0

    aput-object v3, v4, v34

    const/4 v5, 0x3

    .line 694
    new-array v3, v5, [Lkotlin/Pair;

    const-string v5, "Carrie-Anne Moss"

    invoke-static {v13, v5}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v5

    aput-object v5, v3, v34

    const-string v5, "role"

    const-string v6, "Trinity"

    invoke-static {v5, v6}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v5

    aput-object v5, v3, v28

    const-string v5, "avatar"

    const-string v6, "https://image.tmdb.org/t/p/w500/8gRE3687bcu6797hY0A1vGZ6P6V.jpg"

    invoke-static {v5, v6}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v5

    const/16 v27, 0x2

    aput-object v5, v3, v27

    invoke-static {v3}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v3

    aput-object v3, v4, v28

    .line 692
    invoke-static {v4}, Lkotlin/collections/CollectionsKt;->listOf([Ljava/lang/Object;)Ljava/util/List;

    move-result-object v3

    invoke-interface {v0, v2, v3}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 696
    const-string v2, "languages"

    const/4 v3, 0x2

    .line 697
    new-array v4, v3, [Ljava/util/Map;

    const/4 v3, 0x4

    new-array v5, v3, [Lkotlin/Pair;

    const-string v3, "id"

    const-string v6, "sub_en"

    invoke-static {v3, v6}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v3

    const/16 v34, 0x0

    aput-object v3, v5, v34

    invoke-static {v14, v12}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v3

    aput-object v3, v5, v28

    const-string v3, "English Dub"

    invoke-static {v13, v3}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v3

    const/16 v27, 0x2

    aput-object v3, v5, v27

    const-string v3, "type"

    const-string v6, "dub"

    invoke-static {v3, v6}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v3

    const/16 v24, 0x3

    aput-object v3, v5, v24

    invoke-static {v5}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v3

    const/16 v34, 0x0

    aput-object v3, v4, v34

    const/4 v3, 0x4

    .line 698
    new-array v3, v3, [Lkotlin/Pair;

    const-string v5, "id"

    const-string v6, "res_hd"

    invoke-static {v5, v6}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v5

    aput-object v5, v3, v34

    invoke-static {v14, v12}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v5

    aput-object v5, v3, v28

    const-string v5, "Google Resource (1080p)"

    invoke-static {v13, v5}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v5

    const/16 v27, 0x2

    aput-object v5, v3, v27

    const-string v5, "type"

    const-string v6, "resource"

    invoke-static {v5, v6}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v5

    const/16 v24, 0x3

    aput-object v5, v3, v24

    invoke-static {v3}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v3

    aput-object v3, v4, v28

    .line 696
    invoke-static {v4}, Lkotlin/collections/CollectionsKt;->listOf([Ljava/lang/Object;)Ljava/util/List;

    move-result-object v3

    invoke-interface {v0, v2, v3}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    const/4 v2, 0x2

    .line 701
    new-array v2, v2, [Lkotlin/Pair;

    const/16 v34, 0x0

    invoke-static/range {v34 .. v34}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    invoke-static {v15, v3}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v3

    aput-object v3, v2, v34

    .line 702
    invoke-static {v11, v0}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    aput-object v0, v2, v28

    .line 700
    invoke-static {v2}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v0

    .line 704
    new-instance v2, Lkotlin/Pair;

    invoke-static/range {v18 .. v18}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->toJsonString(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    invoke-direct {v2, v3, v0}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V
    :try_end_1473
    .catch Ljava/lang/Exception; {:try_start_132f .. :try_end_1473} :catch_1987

    goto/16 :goto_ca4

    :cond_1475
    move-object/from16 v1, p0

    move-object v14, v5

    move-object/from16 v19, v9

    .line 708
    :try_start_147a
    const-string v2, "/episodes/"

    const/4 v3, 0x2

    const/4 v5, 0x0

    const/4 v9, 0x0

    invoke-static {v8, v2, v9, v3, v5}, Lkotlin/text/StringsKt;->startsWith$default(Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Z

    move-result v2
    :try_end_1483
    .catch Ljava/lang/Exception; {:try_start_147a .. :try_end_1483} :catch_27a1

    if-eqz v2, :cond_198a

    .line 709
    :try_start_1485
    const-string v0, "/episodes/"

    invoke-static {v8, v0, v5, v3, v5}, Lkotlin/text/StringsKt;->substringAfter$default(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    const-string v2, "?"

    invoke-static {v0, v2, v5, v3, v5}, Lkotlin/text/StringsKt;->substringBefore$default(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    const-string v2, "/"

    invoke-static {v0, v2, v5, v3, v5}, Lkotlin/text/StringsKt;->substringBefore$default(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;

    move-result-object v9
    :try_end_1497
    .catch Ljava/lang/Exception; {:try_start_1485 .. :try_end_1497} :catch_1987

    .line 711
    :try_start_1497
    const-string v2, "GET"

    const-string v3, "/wefeed-mobile-bff/subject-api/season-info"

    invoke-static {v14, v9}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    invoke-static {v0}, Lkotlin/collections/MapsKt;->mapOf(Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v4
    :try_end_14a3
    .catch Ljava/lang/Exception; {:try_start_1497 .. :try_end_14a3} :catch_1857

    const/16 v6, 0x8

    move-object v13, v7

    const/4 v7, 0x0

    move-object/from16 v35, v5

    const/4 v5, 0x0

    move-object/from16 v14, p3

    move-object v8, v13

    move-object/from16 v13, v17

    :try_start_14af
    invoke-static/range {v1 .. v7}, Lcom/example/data/network/LocalMovieBoxServer;->makeOfficialRequest$default(Lcom/example/data/network/LocalMovieBoxServer;Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    .line 712
    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->parseJsonToMap(Ljava/lang/String;)Ljava/util/Map;

    move-result-object v0

    .line 713
    invoke-interface {v0, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    instance-of v3, v2, Ljava/util/Map;

    if-eqz v3, :cond_14c3

    move-object v5, v2

    check-cast v5, Ljava/util/Map;

    goto :goto_14c4

    :cond_14c3
    const/4 v5, 0x0

    :goto_14c4
    if-nez v5, :cond_14c7

    goto :goto_14c8

    :cond_14c7
    move-object v0, v5

    .line 715
    :goto_14c8
    const-string v2, "seasons"

    invoke-interface {v0, v2}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    instance-of v3, v2, Ljava/util/List;

    if-eqz v3, :cond_14d6

    move-object v5, v2

    check-cast v5, Ljava/util/List;

    goto :goto_14d7

    :cond_14d6
    const/4 v5, 0x0

    :goto_14d7
    if-nez v5, :cond_14fd

    const-string v2, "seasonList"

    invoke-interface {v0, v2}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    instance-of v3, v2, Ljava/util/List;

    if-eqz v3, :cond_14e7

    move-object v5, v2

    check-cast v5, Ljava/util/List;

    goto :goto_14e8

    :cond_14e7
    const/4 v5, 0x0

    :goto_14e8
    if-nez v5, :cond_14fd

    invoke-interface {v0, v8}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    instance-of v2, v0, Ljava/util/List;

    if-eqz v2, :cond_14f6

    move-object v5, v0

    check-cast v5, Ljava/util/List;

    goto :goto_14f7

    :cond_14f6
    const/4 v5, 0x0

    :goto_14f7
    if-nez v5, :cond_14fd

    invoke-static {}, Lkotlin/collections/CollectionsKt;->emptyList()Ljava/util/List;

    move-result-object v5

    .line 716
    :cond_14fd
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    check-cast v0, Ljava/util/List;

    .line 718
    invoke-interface {v5}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :goto_1508
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_1813

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/util/Map;

    .line 719
    const-string v4, "se"

    invoke-interface {v3, v4}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    if-nez v4, :cond_1528

    const-string v4, "seasonNumber"

    invoke-interface {v3, v4}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    if-nez v4, :cond_1528

    invoke-static/range {v28 .. v28}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v4

    .line 720
    :cond_1528
    new-instance v5, Ljava/util/ArrayList;

    invoke-direct {v5}, Ljava/util/ArrayList;-><init>()V

    check-cast v5, Ljava/util/List;

    .line 723
    const-string v6, "allEp"

    invoke-interface {v3, v6}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    if-eqz v6, :cond_153e

    const-string v6, "allEp"

    invoke-interface {v3, v6}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    goto :goto_1582

    .line 724
    :cond_153e
    const-string v6, "epList"

    invoke-interface {v3, v6}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    if-eqz v6, :cond_154d

    const-string v6, "epList"

    invoke-interface {v3, v6}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    goto :goto_1582

    .line 725
    :cond_154d
    const-string v6, "episodeList"

    invoke-interface {v3, v6}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    if-eqz v6, :cond_155c

    const-string v6, "episodeList"

    invoke-interface {v3, v6}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    goto :goto_1582

    .line 726
    :cond_155c
    const-string v6, "episodes"

    invoke-interface {v3, v6}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    if-eqz v6, :cond_156b

    const-string v6, "episodes"

    invoke-interface {v3, v6}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    goto :goto_1582

    .line 727
    :cond_156b
    invoke-interface {v3, v8}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    if-eqz v6, :cond_1576

    invoke-interface {v3, v8}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    goto :goto_1582

    .line 728
    :cond_1576
    invoke-interface {v3, v14}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    if-eqz v6, :cond_1581

    invoke-interface {v3, v14}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    goto :goto_1582

    :cond_1581
    const/4 v6, 0x0

    .line 732
    :goto_1582
    instance-of v7, v6, Ljava/lang/String;

    if-eqz v7, :cond_163c

    .line 733
    move-object/from16 v38, v6

    check-cast v38, Ljava/lang/CharSequence;

    move/from16 v6, v28

    new-array v7, v6, [Ljava/lang/String;

    const-string v6, ","

    const/16 v34, 0x0

    aput-object v6, v7, v34

    const/16 v42, 0x6

    const/16 v43, 0x0

    const/16 v40, 0x0

    const/16 v41, 0x0

    move-object/from16 v39, v7

    invoke-static/range {v38 .. v43}, Lkotlin/text/StringsKt;->split$default(Ljava/lang/CharSequence;[Ljava/lang/String;ZIILjava/lang/Object;)Ljava/util/List;

    move-result-object v6

    check-cast v6, Ljava/lang/Iterable;

    .line 1897
    invoke-interface {v6}, Ljava/lang/Iterable;->iterator()Ljava/util/Iterator;

    move-result-object v6

    :goto_15a8
    invoke-interface {v6}, Ljava/util/Iterator;->hasNext()Z

    move-result v7

    if-eqz v7, :cond_1636

    invoke-interface {v6}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v7

    check-cast v7, Ljava/lang/String;

    .line 734
    move-object/from16 v16, v7

    check-cast v16, Ljava/lang/CharSequence;

    invoke-interface/range {v16 .. v16}, Ljava/lang/CharSequence;->length()I

    move-result v16

    if-lez v16, :cond_15c1

    const/16 v16, 0x1

    goto :goto_15c3

    :cond_15c1
    const/16 v16, 0x0

    :goto_15c3
    if-eqz v16, :cond_1628

    move-object/from16 p1, v2

    move-object/from16 p3, v6

    const/4 v2, 0x3

    .line 735
    new-array v6, v2, [Lkotlin/Pair;

    const-string v2, "episodeNumber"

    invoke-static {v2, v7}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    const/16 v34, 0x0

    aput-object v2, v6, v34

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    move-object/from16 v16, v6

    const-string v6, "Episode "

    invoke-virtual {v2, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v13, v2}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    const/16 v28, 0x1

    aput-object v2, v16, v28

    const-string v2, "id"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v6, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    move-object/from16 v17, v14

    const-string v14, "_"

    invoke-virtual {v6, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v14, "_"

    invoke-virtual {v6, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v2, v6}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    const/16 v27, 0x2

    aput-object v2, v16, v27

    invoke-static/range {v16 .. v16}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v2

    invoke-interface {v5, v2}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    goto :goto_162e

    :cond_1628
    move-object/from16 p1, v2

    move-object/from16 p3, v6

    move-object/from16 v17, v14

    :goto_162e
    move-object/from16 v2, p1

    move-object/from16 v6, p3

    move-object/from16 v14, v17

    goto/16 :goto_15a8

    :cond_1636
    move-object/from16 p1, v2

    move-object/from16 v17, v14

    goto/16 :goto_174c

    :cond_163c
    move-object/from16 p1, v2

    move-object/from16 v17, v14

    .line 738
    instance-of v2, v6, Ljava/util/List;

    if-eqz v2, :cond_174c

    .line 739
    check-cast v6, Ljava/util/List;

    invoke-interface {v6}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :cond_164a
    :goto_164a
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v6

    if-eqz v6, :cond_174c

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v6

    .line 740
    instance-of v7, v6, Ljava/util/Map;

    if-eqz v7, :cond_16e4

    .line 741
    move-object v7, v6

    check-cast v7, Ljava/util/Map;

    const-string v14, "ep"

    invoke-interface {v7, v14}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v7

    if-nez v7, :cond_1677

    move-object v7, v6

    check-cast v7, Ljava/util/Map;

    const-string v14, "episodeNumber"

    invoke-interface {v7, v14}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v7

    if-nez v7, :cond_1677

    move-object v7, v6

    check-cast v7, Ljava/util/Map;

    const-string v14, "episode_number"

    invoke-interface {v7, v14}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v7

    :cond_1677
    if-eqz v7, :cond_164a

    move-object/from16 p3, v2

    const/4 v14, 0x3

    .line 743
    new-array v2, v14, [Lkotlin/Pair;

    const-string v14, "episodeNumber"

    move-object/from16 v16, v2

    invoke-virtual {v7}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v14, v2}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    const/16 v34, 0x0

    aput-object v2, v16, v34

    check-cast v6, Ljava/util/Map;

    invoke-interface {v6, v13}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    if-nez v2, :cond_16a9

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "Episode "

    invoke-virtual {v2, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    :cond_16a9
    invoke-static {v13, v2}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    const/16 v28, 0x1

    aput-object v2, v16, v28

    const-string v2, "id"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v6, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v14, "_"

    invoke-virtual {v6, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v14, "_"

    invoke-virtual {v6, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v2, v6}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    const/16 v27, 0x2

    aput-object v2, v16, v27

    invoke-static/range {v16 .. v16}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v2

    invoke-interface {v5, v2}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    goto :goto_1748

    :cond_16e4
    move-object/from16 p3, v2

    if-eqz v6, :cond_1748

    const/4 v7, 0x3

    .line 746
    new-array v2, v7, [Lkotlin/Pair;

    const-string v7, "episodeNumber"

    invoke-virtual {v6}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v14

    invoke-static {v7, v14}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v7

    const/16 v34, 0x0

    aput-object v7, v2, v34

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v14, "Episode "

    invoke-virtual {v7, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v13, v7}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v7

    const/16 v28, 0x1

    aput-object v7, v2, v28

    const-string v7, "id"

    new-instance v14, Ljava/lang/StringBuilder;

    invoke-direct {v14}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v14, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v14

    move-object/from16 v16, v2

    const-string v2, "_"

    invoke-virtual {v14, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v14, "_"

    invoke-virtual {v2, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v7, v2}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    const/16 v27, 0x2

    aput-object v2, v16, v27

    invoke-static/range {v16 .. v16}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v2

    invoke-interface {v5, v2}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    :cond_1748
    :goto_1748
    move-object/from16 v2, p3

    goto/16 :goto_164a

    .line 751
    :cond_174c
    :goto_174c
    invoke-interface {v5}, Ljava/util/List;->isEmpty()Z

    move-result v2

    if-eqz v2, :cond_17e4

    .line 752
    const-string v2, "maxEp"

    invoke-interface {v3, v2}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    if-nez v2, :cond_1768

    const-string v2, "max_ep"

    invoke-interface {v3, v2}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    if-nez v2, :cond_1768

    const/16 v34, 0x0

    invoke-static/range {v34 .. v34}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    .line 753
    :cond_1768
    invoke-virtual {v2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v2}, Lkotlin/text/StringsKt;->toDoubleOrNull(Ljava/lang/String;)Ljava/lang/Double;

    move-result-object v2

    if-eqz v2, :cond_1778

    invoke-virtual {v2}, Ljava/lang/Double;->doubleValue()D

    move-result-wide v2

    double-to-int v2, v2

    goto :goto_1779

    :cond_1778
    const/4 v2, 0x0

    :goto_1779
    if-lez v2, :cond_17e4

    const/4 v6, 0x1

    if-gt v6, v2, :cond_17e4

    const/4 v3, 0x1

    :goto_177f
    const/4 v7, 0x3

    .line 756
    new-array v6, v7, [Lkotlin/Pair;

    const-string v7, "episodeNumber"

    invoke-static {v3}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v14

    invoke-static {v7, v14}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v7

    const/16 v34, 0x0

    aput-object v7, v6, v34

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v14, "Episode "

    invoke-virtual {v7, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v13, v7}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v7

    const/16 v28, 0x1

    aput-object v7, v6, v28

    const-string v7, "id"

    new-instance v14, Ljava/lang/StringBuilder;

    invoke-direct {v14}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v14, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v14

    move-object/from16 v16, v6

    const-string v6, "_"

    invoke-virtual {v14, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v14, "_"

    invoke-virtual {v6, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v7, v6}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v6

    const/16 v27, 0x2

    aput-object v6, v16, v27

    invoke-static/range {v16 .. v16}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v6

    invoke-interface {v5, v6}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    if-eq v3, v2, :cond_17e4

    add-int/lit8 v3, v3, 0x1

    goto :goto_177f

    .line 761
    :cond_17e4
    move-object v2, v5

    check-cast v2, Ljava/util/Collection;

    invoke-interface {v2}, Ljava/util/Collection;->isEmpty()Z

    move-result v2

    if-nez v2, :cond_180b

    const/4 v2, 0x2

    .line 762
    new-array v3, v2, [Lkotlin/Pair;

    const-string v2, "seasonNumber"

    invoke-static {v2, v4}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    const/16 v34, 0x0

    aput-object v2, v3, v34

    const-string v2, "episodes"

    invoke-static {v2, v5}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    const/16 v28, 0x1

    aput-object v2, v3, v28

    invoke-static {v3}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v2

    invoke-interface {v0, v2}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    :cond_180b
    move-object/from16 v2, p1

    move-object/from16 v14, v17

    const/16 v28, 0x1

    goto/16 :goto_1508

    .line 765
    :cond_1813
    invoke-interface {v0}, Ljava/util/List;->isEmpty()Z

    move-result v2

    if-nez v2, :cond_184d

    const/4 v2, 0x2

    .line 769
    new-array v3, v2, [Lkotlin/Pair;

    const/16 v34, 0x0

    invoke-static/range {v34 .. v34}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-static {v15, v2}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    aput-object v2, v3, v34

    .line 770
    const-string v2, "seasons"

    invoke-static {v2, v0}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    invoke-static {v0}, Lkotlin/collections/MapsKt;->mapOf(Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v0

    invoke-static {v11, v0}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    const/16 v28, 0x1

    aput-object v0, v3, v28

    .line 768
    invoke-static {v3}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v0

    .line 772
    new-instance v2, Lkotlin/Pair;

    invoke-static/range {v18 .. v18}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->toJsonString(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    invoke-direct {v2, v3, v0}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V

    goto/16 :goto_ca4

    .line 766
    :cond_184d
    new-instance v0, Ljava/lang/Exception;

    const-string v2, "No seasons from official API"

    invoke-direct {v0, v2}, Ljava/lang/Exception;-><init>(Ljava/lang/String;)V

    throw v0
    :try_end_1855
    .catch Ljava/lang/Exception; {:try_start_14af .. :try_end_1855} :catch_1855

    :catch_1855
    move-exception v0

    goto :goto_185a

    :catch_1857
    move-exception v0

    move-object/from16 v13, v17

    .line 774
    :goto_185a
    :try_start_185a
    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v0

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Episodes fallback triggered: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v10, v0}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    const/4 v2, 0x2

    .line 777
    new-array v0, v2, [Lkotlin/Pair;

    const-string v2, "seasonNumber"

    const/16 v28, 0x1

    invoke-static/range {v28 .. v28}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    invoke-static {v2, v3}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    const/16 v34, 0x0

    aput-object v2, v0, v34

    .line 778
    const-string v2, "episodes"

    const/4 v5, 0x3

    .line 779
    new-array v3, v5, [Ljava/util/Map;

    new-array v4, v5, [Lkotlin/Pair;

    const-string v5, "episodeNumber"

    invoke-static {v5, v12}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v5

    const/16 v34, 0x0

    aput-object v5, v4, v34

    const-string v5, "Pilot"

    invoke-static {v13, v5}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v5

    const/16 v28, 0x1

    aput-object v5, v4, v28

    const-string v5, "id"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v6, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v7, "_1_1"

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v5

    const/16 v27, 0x2

    aput-object v5, v4, v27

    invoke-static {v4}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v4

    const/16 v34, 0x0

    aput-object v4, v3, v34

    const/4 v5, 0x3

    .line 780
    new-array v4, v5, [Lkotlin/Pair;

    const-string v5, "episodeNumber"

    const-string v6, "2"

    invoke-static {v5, v6}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v5

    aput-object v5, v4, v34

    const-string v5, "The Beginning"

    invoke-static {v13, v5}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v5

    const/16 v28, 0x1

    aput-object v5, v4, v28

    const-string v5, "id"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v6, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v7, "_1_2"

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v5

    const/16 v27, 0x2

    aput-object v5, v4, v27

    invoke-static {v4}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v4

    const/16 v28, 0x1

    aput-object v4, v3, v28

    const/4 v5, 0x3

    .line 781
    new-array v4, v5, [Lkotlin/Pair;

    const-string v5, "episodeNumber"

    const-string v6, "3"

    invoke-static {v5, v6}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v5

    const/16 v34, 0x0

    aput-object v5, v4, v34

    const-string v5, "Into the Dark"

    invoke-static {v13, v5}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v5

    const/16 v28, 0x1

    aput-object v5, v4, v28

    const-string v5, "id"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v6, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v7, "_1_3"

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v5

    const/16 v27, 0x2

    aput-object v5, v4, v27

    invoke-static {v4}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v4

    aput-object v4, v3, v27

    .line 778
    invoke-static {v3}, Lkotlin/collections/CollectionsKt;->listOf([Ljava/lang/Object;)Ljava/util/List;

    move-result-object v3

    invoke-static {v2, v3}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    const/16 v28, 0x1

    aput-object v2, v0, v28

    .line 776
    invoke-static {v0}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v0

    .line 775
    invoke-static {v0}, Lkotlin/collections/CollectionsKt;->listOf(Ljava/lang/Object;)Ljava/util/List;

    move-result-object v0

    const/4 v2, 0x2

    .line 786
    new-array v2, v2, [Lkotlin/Pair;

    const/16 v34, 0x0

    invoke-static/range {v34 .. v34}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    invoke-static {v15, v3}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v3

    aput-object v3, v2, v34

    .line 787
    const-string v3, "seasons"

    invoke-static {v3, v0}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    invoke-static {v0}, Lkotlin/collections/MapsKt;->mapOf(Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v0

    invoke-static {v11, v0}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    const/16 v28, 0x1

    aput-object v0, v2, v28

    .line 785
    invoke-static {v2}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v0

    .line 789
    new-instance v2, Lkotlin/Pair;

    invoke-static/range {v18 .. v18}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->toJsonString(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    invoke-direct {v2, v3, v0}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V
    :try_end_1985
    .catch Ljava/lang/Exception; {:try_start_185a .. :try_end_1985} :catch_1987

    goto/16 :goto_ca4

    :catch_1987
    move-exception v0

    goto/16 :goto_2bfd

    :cond_198a
    move-object v8, v7

    move-object/from16 v9, v17

    move-object/from16 v17, p3

    .line 793
    :try_start_198f
    const-string v2, "/stream/"

    move-object/from16 v3, p2

    const/4 v5, 0x2

    const/4 v6, 0x0

    const/4 v7, 0x0

    invoke-static {v3, v2, v6, v5, v7}, Lkotlin/text/StringsKt;->startsWith$default(Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_248a

    .line 794
    const-string v0, "/stream/"

    invoke-static {v3, v0, v7, v5, v7}, Lkotlin/text/StringsKt;->substringAfter$default(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    const-string v2, "?"

    invoke-static {v0, v2, v7, v5, v7}, Lkotlin/text/StringsKt;->substringBefore$default(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    const-string v2, "/"

    invoke-static {v0, v2, v7, v5, v7}, Lkotlin/text/StringsKt;->substringBefore$default(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    .line 795
    const-string v2, "season"

    invoke-interface {v13, v2}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/String;

    if-nez v2, :cond_19ba

    move-object v5, v12

    goto :goto_19bb

    :cond_19ba
    move-object v5, v2

    .line 796
    :goto_19bb
    const-string v2, "episode"

    invoke-interface {v13, v2}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/String;

    if-nez v2, :cond_19c6

    move-object v2, v12

    .line 797
    :cond_19c6
    const-string v6, "quality"

    invoke-interface {v13, v6}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    check-cast v6, Ljava/lang/String;
    :try_end_19ce
    .catch Ljava/lang/Exception; {:try_start_198f .. :try_end_19ce} :catch_27a1

    if-nez v6, :cond_19d2

    :try_start_19d0
    const-string v6, "720p"
    :try_end_19d2
    .catch Ljava/lang/Exception; {:try_start_19d0 .. :try_end_19d2} :catch_1987

    .line 798
    :cond_19d2
    :try_start_19d2
    const-string v7, "resource_id"

    invoke-interface {v13, v7}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v7

    move-object v13, v7

    check-cast v13, Ljava/lang/String;
    :try_end_19db
    .catch Ljava/lang/Exception; {:try_start_19d2 .. :try_end_19db} :catch_27a1

    move-object v7, v2

    .line 804
    :try_start_19dc
    const-string v2, "GET"

    const-string v3, "/wefeed-mobile-bff/subject-api/get"

    invoke-static {v14, v0}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v16

    invoke-static/range {v16 .. v16}, Lkotlin/collections/MapsKt;->mapOf(Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v16
    :try_end_19e8
    .catch Ljava/lang/Exception; {:try_start_19dc .. :try_end_19e8} :catch_1a7d

    move-object/from16 v20, v6

    const/16 v6, 0x8

    move-object/from16 v21, v7

    const/4 v7, 0x0

    move-object/from16 v22, v5

    const/4 v5, 0x0

    move-object/from16 p3, v15

    move-object/from16 v15, v17

    move-object/from16 v44, v20

    move-object/from16 v17, v8

    move-object/from16 v20, v10

    const/4 v10, 0x0

    move-object v8, v4

    move-object/from16 v4, v16

    :try_start_1a00
    invoke-static/range {v1 .. v7}, Lcom/example/data/network/LocalMovieBoxServer;->makeOfficialRequest$default(Lcom/example/data/network/LocalMovieBoxServer;Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;

    move-result-object v2

    .line 805
    invoke-direct {v1, v2}, Lcom/example/data/network/LocalMovieBoxServer;->parseJsonToMap(Ljava/lang/String;)Ljava/util/Map;

    move-result-object v2

    .line 806
    invoke-interface {v2, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    instance-of v3, v2, Ljava/util/Map;

    if-eqz v3, :cond_1a14

    move-object v5, v2

    check-cast v5, Ljava/util/Map;

    goto :goto_1a15

    :cond_1a14
    move-object v5, v10

    :goto_1a15
    if-eqz v5, :cond_1a1f

    .line 807
    const-string v2, "subjectType"

    invoke-interface {v5, v2}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    if-nez v2, :cond_1a31

    :cond_1a1f
    if-eqz v5, :cond_1a28

    const-string v2, "type"

    invoke-interface {v5, v2}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    goto :goto_1a29

    :cond_1a28
    move-object v2, v10

    :goto_1a29
    if-nez v2, :cond_1a31

    const/16 v28, 0x1

    invoke-static/range {v28 .. v28}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    .line 808
    :cond_1a31
    invoke-virtual {v2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v2}, Lkotlin/text/StringsKt;->toDoubleOrNull(Ljava/lang/String;)Ljava/lang/Double;

    move-result-object v2

    if-eqz v2, :cond_1a45

    invoke-virtual {v2}, Ljava/lang/Double;->doubleValue()D

    move-result-wide v2
    :try_end_1a3f
    .catch Ljava/lang/Exception; {:try_start_1a00 .. :try_end_1a3f} :catch_1a8d

    double-to-int v2, v2

    const/4 v6, 0x1

    if-ne v2, v6, :cond_1a45

    const/4 v4, 0x1

    goto :goto_1a46

    :cond_1a45
    const/4 v4, 0x0

    :goto_1a46
    if-eqz v5, :cond_1a4f

    .line 810
    :try_start_1a48
    const-string v2, "resourceDetectors"

    invoke-interface {v5, v2}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    goto :goto_1a50

    :cond_1a4f
    move-object v5, v10

    :goto_1a50
    instance-of v2, v5, Ljava/util/List;

    if-eqz v2, :cond_1a57

    check-cast v5, Ljava/util/List;

    goto :goto_1a58

    :cond_1a57
    move-object v5, v10

    :goto_1a58
    if-nez v5, :cond_1a5e

    invoke-static {}, Lkotlin/collections/CollectionsKt;->emptyList()Ljava/util/List;

    move-result-object v5

    .line 811
    :cond_1a5e
    move-object v2, v5

    check-cast v2, Ljava/util/Collection;

    invoke-interface {v2}, Ljava/util/Collection;->isEmpty()Z

    move-result v2

    if-nez v2, :cond_1a8e

    const/4 v6, 0x0

    .line 812
    invoke-interface {v5, v6}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/util/Map;

    const-string v3, "resourceId"

    invoke-interface {v2, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    if-eqz v2, :cond_1a8e

    invoke-virtual {v2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v2
    :try_end_1a7a
    .catch Ljava/lang/Exception; {:try_start_1a48 .. :try_end_1a7a} :catch_1a8e

    if-nez v2, :cond_1a8f

    goto :goto_1a8e

    :catch_1a7d
    move-object/from16 v22, v5

    move-object/from16 v44, v6

    move-object/from16 v21, v7

    move-object/from16 v20, v10

    move-object/from16 p3, v15

    move-object/from16 v15, v17

    const/4 v10, 0x0

    move-object/from16 v17, v8

    move-object v8, v4

    :catch_1a8d
    const/4 v4, 0x0

    :catch_1a8e
    :cond_1a8e
    :goto_1a8e
    move-object v2, v0

    :cond_1a8f
    move/from16 v16, v4

    .line 818
    const-string v3, "url"

    if-eqz v13, :cond_1ac5

    .line 819
    :try_start_1a95
    const-string v4, "dub_"

    const/4 v5, 0x2

    const/4 v6, 0x0

    invoke-static {v13, v4, v6, v5, v10}, Lkotlin/text/StringsKt;->startsWith$default(Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_1aa6

    .line 820
    const-string v0, "dub_"

    invoke-static {v13, v0, v10, v5, v10}, Lkotlin/text/StringsKt;->substringAfter$default(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    goto :goto_1ac5

    .line 821
    :cond_1aa6
    const-string v4, "res_"

    const/4 v6, 0x0

    invoke-static {v13, v4, v6, v5, v10}, Lkotlin/text/StringsKt;->startsWith$default(Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_1ab8

    .line 822
    const-string v4, "res_"

    invoke-static {v13, v4, v10, v5, v10}, Lkotlin/text/StringsKt;->substringAfter$default(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;

    move-result-object v4

    move-object v13, v0

    move-object v5, v4

    goto :goto_1ac7

    :cond_1ab8
    move-object v5, v13

    move-object v13, v0

    goto :goto_1ac7

    :catch_1abb
    move-exception v0

    move-object v10, v3

    move-object/from16 v9, v20

    move-object/from16 v6, v44

    move-object/from16 v3, p3

    goto/16 :goto_23fb

    :cond_1ac5
    :goto_1ac5
    move-object v13, v0

    move-object v5, v10

    :goto_1ac7
    if-eqz v16, :cond_1acb

    move-object v0, v10

    goto :goto_1acd

    :cond_1acb
    move-object/from16 v0, v22

    :goto_1acd
    if-eqz v16, :cond_1ad1

    move-object v4, v10

    goto :goto_1ad3

    :cond_1ad1
    move-object/from16 v4, v21

    :goto_1ad3
    const/4 v6, 0x2

    .line 832
    new-array v7, v6, [Lkotlin/Pair;

    invoke-static {v14, v13}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v6

    const/16 v34, 0x0

    aput-object v6, v7, v34

    .line 833
    const-string v6, "host"

    const-string v10, "api6.aoneroom.com"

    invoke-static {v6, v10}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v6

    const/16 v28, 0x1

    aput-object v6, v7, v28

    .line 831
    invoke-static {v7}, Lkotlin/collections/MapsKt;->mutableMapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v6

    if-eqz v0, :cond_1af5

    .line 835
    const-string v7, "se"

    invoke-interface {v6, v7, v0}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    :cond_1af5
    if-eqz v4, :cond_1afc

    .line 836
    const-string v7, "ep"

    invoke-interface {v6, v7, v4}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    :cond_1afc
    if-eqz v5, :cond_1b03

    .line 837
    const-string v7, "resourceId"

    invoke-interface {v6, v7, v5}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    :cond_1b03
    move-object v5, v2

    .line 839
    const-string v2, "GET"
    :try_end_1b06
    .catch Ljava/lang/Exception; {:try_start_1a95 .. :try_end_1b06} :catch_1abb

    move-object v7, v3

    :try_start_1b07
    const-string v3, "/wefeed-mobile-bff/subject-api/play-info"
    :try_end_1b09
    .catch Ljava/lang/Exception; {:try_start_1b07 .. :try_end_1b09} :catch_23f3

    move-object v10, v4

    move-object v4, v6

    const/16 v6, 0x8

    move-object/from16 v25, v7

    const/4 v7, 0x0

    move-object/from16 v29, v5

    const/4 v5, 0x0

    move-object/from16 p1, v10

    move-object/from16 v10, v25

    move-object/from16 v45, v29

    :try_start_1b19
    invoke-static/range {v1 .. v7}, Lcom/example/data/network/LocalMovieBoxServer;->makeOfficialRequest$default(Lcom/example/data/network/LocalMovieBoxServer;Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;

    move-result-object v2

    .line 840
    invoke-direct {v1, v2}, Lcom/example/data/network/LocalMovieBoxServer;->parseJsonToMap(Ljava/lang/String;)Ljava/util/Map;

    move-result-object v2

    .line 841
    invoke-interface {v2, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    instance-of v4, v3, Ljava/util/Map;

    if-eqz v4, :cond_1b2d

    move-object v5, v3

    check-cast v5, Ljava/util/Map;

    goto :goto_1b2e

    :cond_1b2d
    const/4 v5, 0x0

    :goto_1b2e
    if-nez v5, :cond_1b34

    invoke-static {}, Lkotlin/collections/MapsKt;->emptyMap()Ljava/util/Map;

    move-result-object v5

    :cond_1b34
    move-object v3, v5

    .line 843
    const-string v4, "streamList"

    invoke-interface {v3, v4}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    instance-of v5, v4, Ljava/util/List;

    if-eqz v5, :cond_1b43

    move-object v5, v4

    check-cast v5, Ljava/util/List;

    goto :goto_1b44

    :cond_1b43
    const/4 v5, 0x0

    :goto_1b44
    if-nez v5, :cond_1b5b

    const-string v4, "streams"

    invoke-interface {v3, v4}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    instance-of v5, v4, Ljava/util/List;

    if-eqz v5, :cond_1b54

    move-object v5, v4

    check-cast v5, Ljava/util/List;

    goto :goto_1b55

    :cond_1b54
    const/4 v5, 0x0

    :goto_1b55
    if-nez v5, :cond_1b5b

    invoke-static {}, Lkotlin/collections/CollectionsKt;->emptyList()Ljava/util/List;

    move-result-object v5

    :cond_1b5b
    move-object/from16 v25, v5

    .line 844
    const-string v4, "signCookie"

    invoke-interface {v2, v4}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    if-nez v4, :cond_1b70

    const-string v2, "signCookie"

    invoke-interface {v3, v2}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    if-nez v4, :cond_1b70

    move-object/from16 v29, v26

    goto :goto_1b72

    :cond_1b70
    move-object/from16 v29, v4

    .line 848
    :goto_1b72
    invoke-interface/range {v25 .. v25}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :goto_1b76
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v4
    :try_end_1b7a
    .catch Ljava/lang/Exception; {:try_start_1b19 .. :try_end_1b7a} :catch_23ef

    if-eqz v4, :cond_1bcf

    :try_start_1b7c
    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Ljava/util/Map;

    .line 849
    invoke-interface {v4, v10}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4
    :try_end_1b86
    .catch Ljava/lang/Exception; {:try_start_1b7c .. :try_end_1b86} :catch_23eb

    if-eqz v4, :cond_1b8e

    :try_start_1b88
    invoke-virtual {v4}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v4
    :try_end_1b8c
    .catch Ljava/lang/Exception; {:try_start_1b88 .. :try_end_1b8c} :catch_23ef

    if-nez v4, :cond_1b90

    :cond_1b8e
    move-object/from16 v4, v26

    .line 850
    :cond_1b90
    :try_start_1b90
    move-object v5, v4

    check-cast v5, Ljava/lang/CharSequence;

    const-string v6, "macdn.aoneroom.com"

    check-cast v6, Ljava/lang/CharSequence;

    move-object/from16 p4, v2

    const/4 v1, 0x0

    const/4 v2, 0x0

    const/4 v7, 0x2

    invoke-static {v5, v6, v1, v7, v2}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v5

    if-nez v5, :cond_1bca

    move-object v5, v4

    check-cast v5, Ljava/lang/CharSequence;

    const-string v6, "please_update"

    check-cast v6, Ljava/lang/CharSequence;

    invoke-static {v5, v6, v1, v7, v2}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v5

    if-nez v5, :cond_1bca

    move-object v5, v4

    check-cast v5, Ljava/lang/CharSequence;

    const-string v6, "update_app"

    check-cast v6, Ljava/lang/CharSequence;

    invoke-static {v5, v6, v1, v7, v2}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v5

    if-nez v5, :cond_1bca

    check-cast v4, Ljava/lang/CharSequence;

    const-string v5, "b164fbfb43"

    check-cast v5, Ljava/lang/CharSequence;

    invoke-static {v4, v5, v1, v7, v2}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v4

    if-nez v4, :cond_1bca

    const/4 v4, 0x0

    goto :goto_1bd0

    :cond_1bca
    move-object/from16 v1, p0

    move-object/from16 v2, p4

    goto :goto_1b76

    :cond_1bcf
    const/4 v4, 0x1

    .line 855
    :goto_1bd0
    invoke-interface/range {v25 .. v25}, Ljava/util/List;->isEmpty()Z

    move-result v1
    :try_end_1bd4
    .catch Ljava/lang/Exception; {:try_start_1b90 .. :try_end_1bd4} :catch_23eb

    if-eqz v1, :cond_1bd7

    const/4 v4, 0x1

    :cond_1bd7
    if-eqz v4, :cond_1f57

    .line 859
    :try_start_1bd9
    const-string v2, "GET"
    :try_end_1bdb
    .catch Ljava/lang/Exception; {:try_start_1bd9 .. :try_end_1bdb} :catch_1f2f

    move-object v5, v3

    :try_start_1bdc
    const-string v3, "/wefeed-mobile-bff/ugc/video/first-by-subject"

    invoke-static {v14, v13}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v1

    invoke-static {v1}, Lkotlin/collections/MapsKt;->mapOf(Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v4
    :try_end_1be6
    .catch Ljava/lang/Exception; {:try_start_1bdc .. :try_end_1be6} :catch_1f29

    const/16 v6, 0x8

    const/4 v7, 0x0

    move-object v1, v5

    const/4 v5, 0x0

    move-object/from16 v46, v1

    move-object/from16 v1, p0

    :try_start_1bef
    invoke-static/range {v1 .. v7}, Lcom/example/data/network/LocalMovieBoxServer;->makeOfficialRequest$default(Lcom/example/data/network/LocalMovieBoxServer;Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;

    move-result-object v2

    .line 860
    invoke-direct {v1, v2}, Lcom/example/data/network/LocalMovieBoxServer;->parseJsonToMap(Ljava/lang/String;)Ljava/util/Map;

    move-result-object v2

    .line 861
    invoke-interface {v2, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    instance-of v3, v2, Ljava/util/Map;

    if-eqz v3, :cond_1c03

    move-object v5, v2

    check-cast v5, Ljava/util/Map;

    goto :goto_1c04

    :cond_1c03
    const/4 v5, 0x0

    :goto_1c04
    if-eqz v5, :cond_1c13

    .line 862
    const-string v2, "ugcVideoId"

    invoke-interface {v5, v2}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    if-eqz v2, :cond_1c13

    invoke-virtual {v2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v2

    goto :goto_1c14

    :cond_1c13
    const/4 v2, 0x0

    :goto_1c14
    if-eqz v5, :cond_1c1d

    .line 864
    const-string v3, "belongToCollection"

    invoke-interface {v5, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    goto :goto_1c1e

    :cond_1c1d
    const/4 v5, 0x0

    :goto_1c1e
    instance-of v3, v5, Ljava/util/Map;

    if-eqz v3, :cond_1c25

    check-cast v5, Ljava/util/Map;

    goto :goto_1c26

    :cond_1c25
    const/4 v5, 0x0

    :goto_1c26
    if-eqz v5, :cond_1c35

    .line 865
    const-string v3, "collectionId"

    invoke-interface {v5, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    if-eqz v3, :cond_1c35

    invoke-virtual {v3}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v5

    goto :goto_1c36

    :cond_1c35
    const/4 v5, 0x0

    .line 867
    :goto_1c36
    new-instance v3, Ljava/util/ArrayList;

    invoke-direct {v3}, Ljava/util/ArrayList;-><init>()V

    check-cast v3, Ljava/util/List;

    .line 871
    move-object v4, v5

    check-cast v4, Ljava/lang/CharSequence;

    if-eqz v4, :cond_1c4b

    invoke-interface {v4}, Ljava/lang/CharSequence;->length()I

    move-result v4
    :try_end_1c46
    .catch Ljava/lang/Exception; {:try_start_1bef .. :try_end_1c46} :catch_1f27

    if-nez v4, :cond_1c49

    goto :goto_1c4b

    :cond_1c49
    const/4 v4, 0x0

    goto :goto_1c4c

    :cond_1c4b
    :goto_1c4b
    const/4 v4, 0x1

    :goto_1c4c
    if-nez v4, :cond_1e1b

    move-object v4, v2

    .line 872
    :try_start_1c4f
    const-string v2, "GET"

    move-object v6, v3

    const-string v3, "/wefeed-mobile-bff/ugc/collection/download-list"

    const/4 v7, 0x3

    new-array v1, v7, [Lkotlin/Pair;

    const-string v7, "collectionId"

    invoke-static {v7, v5}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v5

    const/16 v34, 0x0

    aput-object v5, v1, v34

    invoke-static {v8, v12}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v5

    const/16 v28, 0x1

    aput-object v5, v1, v28

    const-string v5, "perPage"

    const-string v7, "50"

    invoke-static {v5, v7}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v5

    const/16 v27, 0x2

    aput-object v5, v1, v27

    invoke-static {v1}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v1
    :try_end_1c79
    .catch Ljava/lang/Exception; {:try_start_1c4f .. :try_end_1c79} :catch_1e14

    move-object v5, v6

    const/16 v6, 0x8

    const/4 v7, 0x0

    move-object v8, v5

    const/4 v5, 0x0

    move-object/from16 v30, v13

    move-object v13, v8

    move-object v8, v4

    move-object v4, v1

    move-object/from16 v1, p0

    :try_start_1c86
    invoke-static/range {v1 .. v7}, Lcom/example/data/network/LocalMovieBoxServer;->makeOfficialRequest$default(Lcom/example/data/network/LocalMovieBoxServer;Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;

    move-result-object v2

    .line 873
    invoke-direct {v1, v2}, Lcom/example/data/network/LocalMovieBoxServer;->parseJsonToMap(Ljava/lang/String;)Ljava/util/Map;

    move-result-object v2

    .line 874
    invoke-interface {v2, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    instance-of v3, v2, Ljava/util/Map;
    :try_end_1c94
    .catch Ljava/lang/Exception; {:try_start_1c86 .. :try_end_1c94} :catch_1ee3

    if-eqz v3, :cond_1c9a

    :try_start_1c96
    move-object v5, v2

    check-cast v5, Ljava/util/Map;

    goto :goto_1c9b

    :cond_1c9a
    const/4 v5, 0x0

    :goto_1c9b
    if-eqz v5, :cond_1ca2

    .line 875
    invoke-interface {v5, v15}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5
    :try_end_1ca1
    .catch Ljava/lang/Exception; {:try_start_1c96 .. :try_end_1ca1} :catch_1f25

    goto :goto_1ca3

    :cond_1ca2
    const/4 v5, 0x0

    :goto_1ca3
    :try_start_1ca3
    instance-of v2, v5, Ljava/util/List;
    :try_end_1ca5
    .catch Ljava/lang/Exception; {:try_start_1ca3 .. :try_end_1ca5} :catch_1ee3

    if-eqz v2, :cond_1caa

    :try_start_1ca7
    check-cast v5, Ljava/util/List;

    goto :goto_1cab

    :cond_1caa
    const/4 v5, 0x0

    :goto_1cab
    if-nez v5, :cond_1cb1

    invoke-static {}, Lkotlin/collections/CollectionsKt;->emptyList()Ljava/util/List;

    move-result-object v5

    :cond_1cb1
    if-nez v0, :cond_1cba

    const/16 v28, 0x1

    .line 878
    invoke-static/range {v28 .. v28}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v0

    goto :goto_1cbc

    :cond_1cba
    const/16 v28, 0x1

    :goto_1cbc
    if-nez p1, :cond_1cc3

    invoke-static/range {v28 .. v28}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v4
    :try_end_1cc2
    .catch Ljava/lang/Exception; {:try_start_1ca7 .. :try_end_1cc2} :catch_1f25

    goto :goto_1cc5

    :cond_1cc3
    move-object/from16 v4, p1

    :goto_1cc5
    :try_start_1cc5
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "S"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v2, "EP"

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0
    :try_end_1ce2
    .catch Ljava/lang/Exception; {:try_start_1cc5 .. :try_end_1ce2} :catch_1ee3

    if-nez p1, :cond_1ceb

    const/16 v28, 0x1

    .line 879
    :try_start_1ce6
    invoke-static/range {v28 .. v28}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v4
    :try_end_1cea
    .catch Ljava/lang/Exception; {:try_start_1ce6 .. :try_end_1cea} :catch_1f25

    goto :goto_1ced

    :cond_1ceb
    move-object/from16 v4, p1

    :goto_1ced
    :try_start_1ced
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "E"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    .line 882
    invoke-interface {v5}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v3

    :goto_1d04
    invoke-interface {v3}, Ljava/util/Iterator;->hasNext()Z

    move-result v4

    if-eqz v4, :cond_1d8f

    invoke-interface {v3}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Ljava/util/Map;

    .line 883
    const-string v6, "epTitle"

    invoke-interface {v4, v6}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6
    :try_end_1d16
    .catch Ljava/lang/Exception; {:try_start_1ced .. :try_end_1d16} :catch_1ee3

    if-eqz v6, :cond_1d2b

    :try_start_1d18
    invoke-virtual {v6}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v6

    if-eqz v6, :cond_1d2b

    sget-object v7, Ljava/util/Locale;->ROOT:Ljava/util/Locale;

    invoke-virtual {v6, v7}, Ljava/lang/String;->toUpperCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object v6

    const-string v7, "toUpperCase(...)"

    invoke-static {v6, v7}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V
    :try_end_1d29
    .catch Ljava/lang/Exception; {:try_start_1d18 .. :try_end_1d29} :catch_1f25

    if-nez v6, :cond_1d2d

    :cond_1d2b
    move-object/from16 v6, v26

    .line 884
    :cond_1d2d
    :try_start_1d2d
    invoke-interface {v4, v9}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v7

    if-eqz v7, :cond_1d46

    invoke-virtual {v7}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v7

    if-eqz v7, :cond_1d46

    sget-object v1, Ljava/util/Locale;->ROOT:Ljava/util/Locale;

    invoke-virtual {v7, v1}, Ljava/lang/String;->toUpperCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object v1

    const-string v7, "toUpperCase(...)"

    invoke-static {v1, v7}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V

    if-nez v1, :cond_1d48

    :cond_1d46
    move-object/from16 v1, v26

    .line 885
    :cond_1d48
    move-object v7, v6

    check-cast v7, Ljava/lang/CharSequence;

    move-object/from16 p4, v1

    move-object v1, v0

    check-cast v1, Ljava/lang/CharSequence;

    move-object/from16 v31, v2

    move-object/from16 v32, v3

    move-object/from16 v33, v4

    const/4 v2, 0x2

    const/4 v3, 0x0

    const/4 v4, 0x0

    invoke-static {v7, v1, v3, v2, v4}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_1d91

    check-cast v6, Ljava/lang/CharSequence;

    move-object/from16 v1, v31

    check-cast v1, Ljava/lang/CharSequence;

    invoke-static {v6, v1, v3, v2, v4}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_1d91

    move-object/from16 v1, p4

    check-cast v1, Ljava/lang/CharSequence;

    move-object v6, v0

    check-cast v6, Ljava/lang/CharSequence;

    invoke-static {v1, v6, v3, v2, v4}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_1d91

    move-object/from16 v1, p4

    check-cast v1, Ljava/lang/CharSequence;

    move-object/from16 v6, v31

    check-cast v6, Ljava/lang/CharSequence;

    invoke-static {v1, v6, v3, v2, v4}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_1d87

    goto :goto_1d91

    :cond_1d87
    move-object/from16 v1, p0

    move-object/from16 v2, v31

    move-object/from16 v3, v32

    goto/16 :goto_1d04

    :cond_1d8f
    const/16 v33, 0x0

    :cond_1d91
    :goto_1d91
    if-nez v33, :cond_1dad

    move-object/from16 v0, p1

    .line 892
    invoke-static {v0, v12}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_1dad

    move-object v0, v5

    check-cast v0, Ljava/util/Collection;

    invoke-interface {v0}, Ljava/util/Collection;->isEmpty()Z

    move-result v0

    if-nez v0, :cond_1dad

    const/4 v6, 0x0

    .line 893
    invoke-interface {v5, v6}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    move-object/from16 v33, v0

    check-cast v33, Ljava/util/Map;

    :cond_1dad
    move-object/from16 v0, v33

    if-eqz v0, :cond_1e1f

    .line 898
    const-string v1, "resources"

    invoke-interface {v0, v1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    instance-of v1, v0, Ljava/util/List;

    if-eqz v1, :cond_1dbf

    move-object v5, v0

    check-cast v5, Ljava/util/List;

    goto :goto_1dc0

    :cond_1dbf
    const/4 v5, 0x0

    :goto_1dc0
    if-nez v5, :cond_1dc6

    invoke-static {}, Lkotlin/collections/CollectionsKt;->emptyList()Ljava/util/List;

    move-result-object v5

    .line 899
    :cond_1dc6
    invoke-interface {v5}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :cond_1dca
    :goto_1dca
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_1e12

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/util/Map;

    .line 900
    invoke-interface {v1, v10}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    if-eqz v2, :cond_1de2

    invoke-virtual {v2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v4

    if-nez v4, :cond_1de4

    :cond_1de2
    move-object/from16 v4, v26

    .line 901
    :cond_1de4
    move-object v2, v4

    check-cast v2, Ljava/lang/CharSequence;

    invoke-interface {v2}, Ljava/lang/CharSequence;->length()I

    move-result v2

    if-lez v2, :cond_1def

    const/4 v2, 0x1

    goto :goto_1df0

    :cond_1def
    const/4 v2, 0x0

    :goto_1df0
    if-eqz v2, :cond_1dca

    move-object v2, v4

    check-cast v2, Ljava/lang/CharSequence;

    const-string v3, "macdn.aoneroom.com"

    check-cast v3, Ljava/lang/CharSequence;

    const/4 v5, 0x2

    const/4 v6, 0x0

    const/4 v12, 0x0

    invoke-static {v2, v3, v6, v5, v12}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v2

    if-nez v2, :cond_1dca

    check-cast v4, Ljava/lang/CharSequence;

    const-string v2, "b164fbfb43"

    check-cast v2, Ljava/lang/CharSequence;

    invoke-static {v4, v2, v6, v5, v12}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v2

    if-nez v2, :cond_1dca

    .line 902
    invoke-interface {v13, v1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    goto :goto_1dca

    :cond_1e12
    const/4 v4, 0x1

    goto :goto_1e20

    :catch_1e14
    move-exception v0

    move-object/from16 v30, v13

    :goto_1e17
    move-object/from16 v1, p0

    goto/16 :goto_1f36

    :cond_1e1b
    move-object v8, v2

    move-object/from16 v30, v13

    move-object v13, v3

    :cond_1e1f
    const/4 v4, 0x0

    :goto_1e20
    if-nez v4, :cond_1ee6

    .line 909
    move-object v2, v8

    check-cast v2, Ljava/lang/CharSequence;

    if-eqz v2, :cond_1e30

    invoke-interface {v2}, Ljava/lang/CharSequence;->length()I

    move-result v0

    if-nez v0, :cond_1e2e

    goto :goto_1e30

    :cond_1e2e
    const/4 v4, 0x0

    goto :goto_1e31

    :cond_1e30
    :goto_1e30
    const/4 v4, 0x1

    :goto_1e31
    if-nez v4, :cond_1ee6

    .line 910
    const-string v2, "GET"

    const-string v3, "/wefeed-mobile-bff/ugc/video/download"

    const-string v0, "ugcVideoId"

    invoke-static {v0, v8}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    invoke-static {v0}, Lkotlin/collections/MapsKt;->mapOf(Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v4
    :try_end_1e41
    .catch Ljava/lang/Exception; {:try_start_1d2d .. :try_end_1e41} :catch_1ee3

    const/16 v6, 0x8

    const/4 v7, 0x0

    const/4 v5, 0x0

    move-object/from16 v1, p0

    :try_start_1e47
    invoke-static/range {v1 .. v7}, Lcom/example/data/network/LocalMovieBoxServer;->makeOfficialRequest$default(Lcom/example/data/network/LocalMovieBoxServer;Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    .line 911
    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->parseJsonToMap(Ljava/lang/String;)Ljava/util/Map;

    move-result-object v0

    .line 912
    invoke-interface {v0, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    instance-of v2, v0, Ljava/util/Map;

    if-eqz v2, :cond_1e5b

    move-object v5, v0

    check-cast v5, Ljava/util/Map;

    goto :goto_1e5c

    :cond_1e5b
    const/4 v5, 0x0

    :goto_1e5c
    if-eqz v5, :cond_1e63

    .line 913
    invoke-interface {v5, v15}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    goto :goto_1e64

    :cond_1e63
    const/4 v5, 0x0

    :goto_1e64
    instance-of v0, v5, Ljava/util/List;

    if-eqz v0, :cond_1e6b

    check-cast v5, Ljava/util/List;

    goto :goto_1e6c

    :cond_1e6b
    const/4 v5, 0x0

    :goto_1e6c
    if-nez v5, :cond_1e72

    invoke-static {}, Lkotlin/collections/CollectionsKt;->emptyList()Ljava/util/List;

    move-result-object v5

    .line 914
    :cond_1e72
    invoke-interface {v5}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :cond_1e76
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    if-eqz v2, :cond_1ee8

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/util/Map;

    .line 915
    const-string v3, "resources"

    invoke-interface {v2, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    instance-of v3, v2, Ljava/util/List;

    if-eqz v3, :cond_1e90

    move-object v5, v2

    check-cast v5, Ljava/util/List;

    goto :goto_1e91

    :cond_1e90
    const/4 v5, 0x0

    :goto_1e91
    if-nez v5, :cond_1e97

    invoke-static {}, Lkotlin/collections/CollectionsKt;->emptyList()Ljava/util/List;

    move-result-object v5

    .line 916
    :cond_1e97
    invoke-interface {v5}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :cond_1e9b
    :goto_1e9b
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_1e76

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/util/Map;

    .line 917
    invoke-interface {v3, v10}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    if-eqz v4, :cond_1eb3

    invoke-virtual {v4}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v4

    if-nez v4, :cond_1eb5

    :cond_1eb3
    move-object/from16 v4, v26

    .line 918
    :cond_1eb5
    move-object v5, v4

    check-cast v5, Ljava/lang/CharSequence;

    invoke-interface {v5}, Ljava/lang/CharSequence;->length()I

    move-result v5

    if-lez v5, :cond_1ec0

    const/4 v5, 0x1

    goto :goto_1ec1

    :cond_1ec0
    const/4 v5, 0x0

    :goto_1ec1
    if-eqz v5, :cond_1e9b

    move-object v5, v4

    check-cast v5, Ljava/lang/CharSequence;

    const-string v6, "macdn.aoneroom.com"

    check-cast v6, Ljava/lang/CharSequence;

    const/4 v7, 0x2

    const/4 v9, 0x0

    const/4 v12, 0x0

    invoke-static {v5, v6, v9, v7, v12}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v5

    if-nez v5, :cond_1e9b

    check-cast v4, Ljava/lang/CharSequence;

    const-string v5, "b164fbfb43"

    check-cast v5, Ljava/lang/CharSequence;

    invoke-static {v4, v5, v9, v7, v12}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v4

    if-nez v4, :cond_1e9b

    .line 919
    invoke-interface {v13, v3}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    goto :goto_1e9b

    :catch_1ee3
    move-exception v0

    goto/16 :goto_1e17

    :cond_1ee6
    move-object/from16 v1, p0

    .line 925
    :cond_1ee8
    move-object v3, v13

    check-cast v3, Ljava/util/Collection;

    invoke-interface {v3}, Ljava/util/Collection;->isEmpty()Z

    move-result v0
    :try_end_1eef
    .catch Ljava/lang/Exception; {:try_start_1e47 .. :try_end_1eef} :catch_1f25

    if-nez v0, :cond_1f1e

    .line 927
    :try_start_1ef1
    invoke-interface {v13}, Ljava/util/List;->size()I

    move-result v0

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "UGC BYPASS SUCCESS: Recovered "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v2, " REAL streams!"

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0
    :try_end_1f0e
    .catch Ljava/lang/Exception; {:try_start_1ef1 .. :try_end_1f0e} :catch_1f18

    move-object/from16 v9, v20

    :try_start_1f10
    invoke-static {v9, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_1f13
    .catch Ljava/lang/Exception; {:try_start_1f10 .. :try_end_1f13} :catch_1f16

    move-object/from16 v25, v13

    goto :goto_1f20

    :catch_1f16
    move-exception v0

    goto :goto_1f1b

    :catch_1f18
    move-exception v0

    move-object/from16 v9, v20

    :goto_1f1b
    move-object/from16 v25, v13

    goto :goto_1f38

    :cond_1f1e
    move-object/from16 v9, v20

    :goto_1f20
    :try_start_1f20
    sget-object v0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;
    :try_end_1f22
    .catch Ljava/lang/Exception; {:try_start_1f20 .. :try_end_1f22} :catch_1f23

    goto :goto_1f5f

    :catch_1f23
    move-exception v0

    goto :goto_1f38

    :catch_1f25
    move-exception v0

    goto :goto_1f36

    :catch_1f27
    move-exception v0

    goto :goto_1f34

    :catch_1f29
    move-exception v0

    move-object/from16 v1, p0

    move-object/from16 v46, v5

    goto :goto_1f34

    :catch_1f2f
    move-exception v0

    move-object/from16 v1, p0

    move-object/from16 v46, v3

    :goto_1f34
    move-object/from16 v30, v13

    :goto_1f36
    move-object/from16 v9, v20

    .line 930
    :goto_1f38
    :try_start_1f38
    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v0

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "UGC bypass failed: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v9, v0}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    goto :goto_1f5f

    :cond_1f57
    move-object/from16 v1, p0

    move-object/from16 v46, v3

    move-object/from16 v30, v13

    move-object/from16 v9, v20

    .line 934
    :goto_1f5f
    move-object/from16 v0, v25

    check-cast v0, Ljava/lang/Iterable;

    .line 1899
    new-instance v2, Ljava/util/ArrayList;

    const/16 v3, 0xa

    invoke-static {v0, v3}, Lkotlin/collections/CollectionsKt;->collectionSizeOrDefault(Ljava/lang/Iterable;I)I

    move-result v3

    invoke-direct {v2, v3}, Ljava/util/ArrayList;-><init>(I)V

    check-cast v2, Ljava/util/Collection;

    .line 1900
    invoke-interface {v0}, Ljava/lang/Iterable;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_1f74
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_1f84

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    .line 1901
    check-cast v3, Ljava/util/Map;

    invoke-interface {v2, v3}, Ljava/util/Collection;->add(Ljava/lang/Object;)Z

    goto :goto_1f74

    .line 1902
    :cond_1f84
    move-object v8, v2

    check-cast v8, Ljava/util/List;

    .line 936
    invoke-virtual/range {v29 .. v29}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v2

    .line 938
    move-object v0, v8

    check-cast v0, Ljava/util/Collection;

    invoke-interface {v0}, Ljava/util/Collection;->isEmpty()Z

    move-result v0

    if-nez v0, :cond_2124

    .line 939
    move-object v0, v8

    check-cast v0, Ljava/lang/Iterable;

    new-instance v3, Lcom/example/data/network/LocalMovieBoxServer$$ExternalSyntheticLambda3;

    invoke-direct {v3}, Lcom/example/data/network/LocalMovieBoxServer$$ExternalSyntheticLambda3;-><init>()V

    new-instance v4, Lcom/example/data/network/LocalMovieBoxServer$$ExternalSyntheticLambda4;

    invoke-direct {v4, v3}, Lcom/example/data/network/LocalMovieBoxServer$$ExternalSyntheticLambda4;-><init>(Lkotlin/jvm/functions/Function2;)V

    invoke-static {v0, v4}, Lkotlin/collections/CollectionsKt;->sortedWith(Ljava/lang/Iterable;Ljava/util/Comparator;)Ljava/util/List;

    move-result-object v0

    const/4 v6, 0x0

    .line 953
    invoke-interface {v0, v6}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/util/Map;

    .line 955
    invoke-interface {v0, v10}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    if-eqz v3, :cond_1fb8

    invoke-virtual {v3}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v4

    if-nez v4, :cond_1fba

    :cond_1fb8
    move-object/from16 v4, v26

    .line 956
    :cond_1fba
    const-string v3, "signCookie"

    invoke-interface {v0, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    if-eqz v0, :cond_1fcb

    invoke-virtual {v0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v0

    if-nez v0, :cond_1fc9

    goto :goto_1fcb

    :cond_1fc9
    move-object v3, v0

    goto :goto_1fcd

    :cond_1fcb
    :goto_1fcb
    move-object/from16 v3, v26

    .line 957
    :goto_1fcd
    move-object v0, v4

    check-cast v0, Ljava/lang/CharSequence;

    const-string v5, "macdn.aoneroom.com"

    check-cast v5, Ljava/lang/CharSequence;

    const/4 v6, 0x0

    const/4 v7, 0x2

    const/4 v12, 0x0

    invoke-static {v0, v5, v6, v7, v12}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_1ffa

    move-object v0, v4

    check-cast v0, Ljava/lang/CharSequence;

    const-string v5, "please_update"

    check-cast v5, Ljava/lang/CharSequence;

    invoke-static {v0, v5, v6, v7, v12}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_1ffa

    move-object v0, v4

    check-cast v0, Ljava/lang/CharSequence;

    const-string v5, "update_app"

    check-cast v5, Ljava/lang/CharSequence;

    invoke-static {v0, v5, v6, v7, v12}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_1ff8

    goto :goto_1ffa

    :cond_1ff8
    const/4 v5, 0x0

    goto :goto_1ffb

    :cond_1ffa
    :goto_1ffa
    const/4 v5, 0x1

    .line 960
    :goto_1ffb
    move-object v0, v3

    check-cast v0, Ljava/lang/CharSequence;

    const-string v6, "urlprefix="

    check-cast v6, Ljava/lang/CharSequence;

    const/4 v7, 0x2

    const/4 v12, 0x0

    const/4 v13, 0x0

    invoke-static {v0, v6, v12, v7, v13}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v0
    :try_end_2009
    .catch Ljava/lang/Exception; {:try_start_1f38 .. :try_end_2009} :catch_23e7

    if-eqz v0, :cond_20f6

    .line 962
    :try_start_200b
    move-object/from16 v36, v3

    check-cast v36, Ljava/lang/CharSequence;

    const-string v37, "urlprefix="

    const/16 v40, 0x6

    const/16 v41, 0x0

    const/16 v38, 0x0

    const/16 v39, 0x0

    invoke-static/range {v36 .. v41}, Lkotlin/text/StringsKt;->indexOf$default(Ljava/lang/CharSequence;Ljava/lang/String;IZILjava/lang/Object;)I

    move-result v0

    add-int/lit8 v38, v0, 0xa

    .line 963
    move-object/from16 v36, v3

    check-cast v36, Ljava/lang/CharSequence;

    const-string v37, ":"

    const/16 v40, 0x4

    const/16 v41, 0x0

    const/16 v39, 0x0

    invoke-static/range {v36 .. v41}, Lkotlin/text/StringsKt;->indexOf$default(Ljava/lang/CharSequence;Ljava/lang/String;IZILjava/lang/Object;)I

    move-result v0

    const/4 v6, -0x1

    if-ne v0, v6, :cond_2045

    .line 964
    move-object/from16 v36, v3

    check-cast v36, Ljava/lang/CharSequence;

    const-string v37, ";"

    const/16 v40, 0x4

    const/16 v41, 0x0

    const/16 v39, 0x0

    invoke-static/range {v36 .. v41}, Lkotlin/text/StringsKt;->indexOf$default(Ljava/lang/CharSequence;Ljava/lang/String;IZILjava/lang/Object;)I

    move-result v0

    move/from16 v6, v38

    goto :goto_2047

    :cond_2045
    move/from16 v6, v38

    :goto_2047
    const/4 v7, -0x1

    if-ne v0, v7, :cond_204e

    .line 965
    invoke-virtual {v3}, Ljava/lang/String;->length()I

    move-result v0

    .line 966
    :cond_204e
    invoke-virtual {v3, v6, v0}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v0

    const-string v6, "substring(...)"

    invoke-static {v0, v6}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V

    .line 968
    invoke-virtual {v0}, Ljava/lang/String;->length()I

    move-result v6

    const/16 v23, 0x4

    rem-int/lit8 v6, v6, 0x4

    rsub-int/lit8 v6, v6, 0x4

    rem-int/lit8 v6, v6, 0x4

    .line 969
    const-string v7, "="

    check-cast v7, Ljava/lang/CharSequence;

    invoke-static {v7, v6}, Lkotlin/text/StringsKt;->repeat(Ljava/lang/CharSequence;I)Ljava/lang/String;

    move-result-object v6

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v7, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const/4 v6, 0x0

    .line 971
    invoke-static {v0, v6}, Landroid/util/Base64;->decode(Ljava/lang/String;I)[B

    move-result-object v0

    .line 972
    invoke-static {v0}, Lkotlin/jvm/internal/Intrinsics;->checkNotNull(Ljava/lang/Object;)V

    sget-object v6, Ljava/nio/charset/StandardCharsets;->UTF_8:Ljava/nio/charset/Charset;

    const-string v7, "UTF_8"

    invoke-static {v6, v7}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V

    new-instance v7, Ljava/lang/String;

    invoke-direct {v7, v0, v6}, Ljava/lang/String;-><init>([BLjava/nio/charset/Charset;)V

    .line 973
    const-string v0, "/"

    const/4 v6, 0x2

    const/4 v12, 0x0

    const/4 v13, 0x0

    invoke-static {v7, v0, v12, v6, v13}, Lkotlin/text/StringsKt;->endsWith$default(Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_20ab

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v0, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v6, "index.m3u8"

    invoke-virtual {v0, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    goto :goto_20ba

    :cond_20ab
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v0, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v6, "/index.m3u8"

    invoke-virtual {v0, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    :goto_20ba
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0
    :try_end_20be
    .catch Ljava/lang/Exception; {:try_start_200b .. :try_end_20be} :catch_20db

    move-object v4, v0

    .line 975
    :try_start_20bf
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "Dummy video bypassed. Real URL: "

    invoke-virtual {v0, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v9, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_20d5
    .catch Ljava/lang/Exception; {:try_start_20bf .. :try_end_20d5} :catch_20d8

    move-object v0, v4

    const/4 v4, 0x0

    goto :goto_20f8

    :catch_20d8
    move-exception v0

    const/4 v5, 0x0

    goto :goto_20dc

    :catch_20db
    move-exception v0

    .line 977
    :goto_20dc
    :try_start_20dc
    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v0

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "Failed to bypass dummy video: "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v9, v0}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    :cond_20f6
    move-object v0, v4

    move v4, v5

    :goto_20f8
    if-eqz v4, :cond_2113

    .line 982
    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "Skipping un-bypassed dummy video trap: "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v9, v0}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    move-object/from16 v4, v26

    goto :goto_2114

    :cond_2113
    move-object v4, v0

    .line 988
    :goto_2114
    move-object v0, v3

    check-cast v0, Ljava/lang/CharSequence;

    invoke-interface {v0}, Ljava/lang/CharSequence;->length()I

    move-result v0

    if-lez v0, :cond_211f

    const/4 v0, 0x1

    goto :goto_2120

    :cond_211f
    const/4 v0, 0x0

    :goto_2120
    if-eqz v0, :cond_2126

    move-object v2, v3

    goto :goto_2126

    :cond_2124
    move-object/from16 v4, v26

    .line 993
    :cond_2126
    :goto_2126
    move-object v0, v4

    check-cast v0, Ljava/lang/CharSequence;

    invoke-interface {v0}, Ljava/lang/CharSequence;->length()I

    move-result v0

    if-nez v0, :cond_2131

    const/4 v0, 0x1

    goto :goto_2132

    :cond_2131
    const/4 v0, 0x0

    :goto_2132
    if-eqz v0, :cond_21ac

    move-object v0, v8

    check-cast v0, Ljava/util/Collection;

    invoke-interface {v0}, Ljava/util/Collection;->isEmpty()Z

    move-result v0

    if-nez v0, :cond_21ac

    .line 995
    invoke-interface {v8}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :cond_2141
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_21ac

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/util/Map;

    .line 996
    invoke-interface {v3, v10}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    if-eqz v5, :cond_2159

    invoke-virtual {v5}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v5

    if-nez v5, :cond_215b

    :cond_2159
    move-object/from16 v5, v26

    .line 997
    :cond_215b
    move-object v6, v5

    check-cast v6, Ljava/lang/CharSequence;

    const-string v7, "macdn.aoneroom.com"

    check-cast v7, Ljava/lang/CharSequence;

    const/4 v12, 0x2

    const/4 v13, 0x0

    const/4 v15, 0x0

    invoke-static {v6, v7, v13, v12, v15}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v6

    if-nez v6, :cond_2188

    move-object v6, v5

    check-cast v6, Ljava/lang/CharSequence;

    const-string v7, "please_update"

    check-cast v7, Ljava/lang/CharSequence;

    invoke-static {v6, v7, v13, v12, v15}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v6

    if-nez v6, :cond_2188

    move-object v6, v5

    check-cast v6, Ljava/lang/CharSequence;

    const-string v7, "update_app"

    check-cast v7, Ljava/lang/CharSequence;

    invoke-static {v6, v7, v13, v12, v15}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v6

    if-eqz v6, :cond_2186

    goto :goto_2188

    :cond_2186
    const/4 v6, 0x0

    goto :goto_2189

    :cond_2188
    :goto_2188
    const/4 v6, 0x1

    :goto_2189
    if-nez v6, :cond_2141

    .line 998
    move-object v6, v5

    check-cast v6, Ljava/lang/CharSequence;

    invoke-interface {v6}, Ljava/lang/CharSequence;->length()I

    move-result v6

    if-lez v6, :cond_2196

    const/4 v6, 0x1

    goto :goto_2197

    :cond_2196
    const/4 v6, 0x0

    :goto_2197
    if-eqz v6, :cond_2141

    .line 1000
    const-string v0, "signCookie"

    invoke-interface {v3, v0}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    if-eqz v0, :cond_21a7

    invoke-virtual {v0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v4

    if-nez v4, :cond_21a9

    :cond_21a7
    move-object/from16 v4, v26

    :cond_21a9
    move-object v12, v4

    move-object v13, v5

    goto :goto_21ae

    :cond_21ac
    move-object v12, v2

    move-object v13, v4

    .line 1006
    :goto_21ae
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    move-object v15, v0

    check-cast v15, Ljava/util/List;
    :try_end_21b6
    .catch Ljava/lang/Exception; {:try_start_20dc .. :try_end_21b6} :catch_23e7

    const/4 v2, 0x2

    .line 1011
    :try_start_21b7
    new-array v0, v2, [Lkotlin/Pair;

    move-object/from16 v2, v30

    invoke-static {v14, v2}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    const/16 v34, 0x0

    aput-object v2, v0, v34

    .line 1012
    const-string v2, "resourceId"

    move-object/from16 v5, v45

    invoke-static {v2, v5}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    const/16 v28, 0x1

    aput-object v2, v0, v28

    .line 1010
    invoke-static {v0}, Lkotlin/collections/MapsKt;->mutableMapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v4

    if-nez v16, :cond_21e3

    .line 1015
    const-string v0, "se"

    move-object/from16 v2, v22

    invoke-interface {v4, v0, v2}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 1016
    const-string v0, "ep"

    move-object/from16 v7, v21

    invoke-interface {v4, v0, v7}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 1018
    :cond_21e3
    const-string v2, "GET"

    const-string v3, "/wefeed-mobile-bff/subject-api/get-ext-captions"

    const/16 v6, 0x8

    const/4 v7, 0x0

    const/4 v5, 0x0

    invoke-static/range {v1 .. v7}, Lcom/example/data/network/LocalMovieBoxServer;->makeOfficialRequest$default(Lcom/example/data/network/LocalMovieBoxServer;Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    .line 1019
    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->parseJsonToMap(Ljava/lang/String;)Ljava/util/Map;

    move-result-object v0

    .line 1020
    invoke-interface {v0, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    instance-of v2, v0, Ljava/util/Map;

    if-eqz v2, :cond_21ff

    move-object v5, v0

    check-cast v5, Ljava/util/Map;

    goto :goto_2200

    :cond_21ff
    const/4 v5, 0x0

    :goto_2200
    if-nez v5, :cond_2206

    invoke-static {}, Lkotlin/collections/MapsKt;->emptyMap()Ljava/util/Map;

    move-result-object v5

    .line 1021
    :cond_2206
    const-string v0, "extCaptions"

    invoke-interface {v5, v0}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    if-nez v0, :cond_221a

    move-object/from16 v14, v17

    invoke-interface {v5, v14}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    if-nez v0, :cond_221a

    invoke-static {}, Lkotlin/collections/CollectionsKt;->emptyList()Ljava/util/List;

    move-result-object v0

    :cond_221a
    instance-of v2, v0, Ljava/util/List;

    if-eqz v2, :cond_2222

    move-object v5, v0

    check-cast v5, Ljava/util/List;

    goto :goto_2223

    :cond_2222
    const/4 v5, 0x0

    :goto_2223
    if-nez v5, :cond_2229

    invoke-static {}, Lkotlin/collections/CollectionsKt;->emptyList()Ljava/util/List;

    move-result-object v5

    .line 1023
    :cond_2229
    invoke-interface {v5}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :cond_222d
    :goto_222d
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    if-eqz v2, :cond_229c

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/util/Map;

    .line 1024
    const-string v3, "lan"

    invoke-interface {v2, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    if-eqz v3, :cond_2247

    invoke-virtual {v3}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v4

    if-nez v4, :cond_2249

    :cond_2247
    move-object/from16 v4, v26

    .line 1025
    :cond_2249
    const-string v3, "lanName"

    invoke-interface {v2, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    if-eqz v3, :cond_2257

    invoke-virtual {v3}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v3

    if-nez v3, :cond_2259

    :cond_2257
    move-object/from16 v3, v26

    .line 1026
    :cond_2259
    invoke-interface {v2, v10}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    if-eqz v2, :cond_2265

    invoke-virtual {v2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v2

    if-nez v2, :cond_2267

    :cond_2265
    move-object/from16 v2, v26

    .line 1027
    :cond_2267
    move-object v5, v2

    check-cast v5, Ljava/lang/CharSequence;

    invoke-interface {v5}, Ljava/lang/CharSequence;->length()I

    move-result v5

    if-lez v5, :cond_2272

    const/4 v5, 0x1

    goto :goto_2273

    :cond_2272
    const/4 v5, 0x0

    :goto_2273
    if-eqz v5, :cond_222d

    const/4 v5, 0x3

    .line 1029
    new-array v6, v5, [Lkotlin/Pair;

    const-string v5, "lan"

    invoke-static {v5, v4}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v4

    const/16 v34, 0x0

    aput-object v4, v6, v34

    .line 1030
    const-string v4, "lanName"

    invoke-static {v4, v3}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v3

    const/16 v28, 0x1

    aput-object v3, v6, v28

    .line 1031
    invoke-static {v10, v2}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    const/16 v27, 0x2

    aput-object v2, v6, v27

    .line 1028
    invoke-static {v6}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v2

    invoke-interface {v15, v2}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    goto :goto_222d

    :cond_229c
    sget-object v0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;
    :try_end_229e
    .catch Ljava/lang/Exception; {:try_start_21b7 .. :try_end_229e} :catch_229f

    goto :goto_22ba

    :catch_229f
    move-exception v0

    .line 1036
    :try_start_22a0
    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v0

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Failed to fetch external subtitles: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v9, v0}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 1040
    :goto_22ba
    const-string v0, "subTitleList"

    move-object/from16 v5, v46

    invoke-interface {v5, v0}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    instance-of v2, v0, Ljava/util/List;

    if-eqz v2, :cond_22ca

    move-object v5, v0

    check-cast v5, Ljava/util/List;

    goto :goto_22cb

    :cond_22ca
    const/4 v5, 0x0

    :goto_22cb
    if-nez v5, :cond_22d1

    invoke-static {}, Lkotlin/collections/CollectionsKt;->emptyList()Ljava/util/List;

    move-result-object v5

    .line 1041
    :cond_22d1
    invoke-interface {v5}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :cond_22d5
    :goto_22d5
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    if-eqz v2, :cond_2374

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/util/Map;

    .line 1042
    const-string v3, "lan"

    invoke-interface {v2, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    if-eqz v3, :cond_22ef

    invoke-virtual {v3}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v4

    if-nez v4, :cond_22f1

    :cond_22ef
    move-object/from16 v4, v26

    .line 1043
    :cond_22f1
    const-string v3, "lanName"

    invoke-interface {v2, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    if-eqz v3, :cond_22ff

    invoke-virtual {v3}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v3

    if-nez v3, :cond_2301

    :cond_22ff
    move-object/from16 v3, v26

    .line 1044
    :cond_2301
    invoke-interface {v2, v10}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    if-eqz v2, :cond_230d

    invoke-virtual {v2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v2

    if-nez v2, :cond_230f

    :cond_230d
    move-object/from16 v2, v26

    .line 1045
    :cond_230f
    move-object v5, v2

    check-cast v5, Ljava/lang/CharSequence;

    invoke-interface {v5}, Ljava/lang/CharSequence;->length()I

    move-result v5

    if-lez v5, :cond_231a

    const/4 v5, 0x1

    goto :goto_231b

    :cond_231a
    const/4 v5, 0x0

    :goto_231b
    if-eqz v5, :cond_22d5

    move-object v5, v15

    check-cast v5, Ljava/lang/Iterable;

    .line 1903
    instance-of v6, v5, Ljava/util/Collection;

    if-eqz v6, :cond_232f

    move-object v6, v5

    check-cast v6, Ljava/util/Collection;

    invoke-interface {v6}, Ljava/util/Collection;->isEmpty()Z

    move-result v6

    if-eqz v6, :cond_232f

    :cond_232d
    const/4 v5, 0x0

    goto :goto_234a

    .line 1904
    :cond_232f
    invoke-interface {v5}, Ljava/lang/Iterable;->iterator()Ljava/util/Iterator;

    move-result-object v5

    :cond_2333
    invoke-interface {v5}, Ljava/util/Iterator;->hasNext()Z

    move-result v6

    if-eqz v6, :cond_232d

    invoke-interface {v5}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v6

    check-cast v6, Ljava/util/Map;

    .line 1045
    invoke-interface {v6, v10}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    invoke-static {v6, v2}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v6

    if-eqz v6, :cond_2333

    const/4 v5, 0x1

    :goto_234a
    if-nez v5, :cond_22d5

    const/4 v5, 0x3

    .line 1047
    new-array v6, v5, [Lkotlin/Pair;

    const-string v5, "lan"

    invoke-static {v5, v4}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v4

    const/16 v34, 0x0

    aput-object v4, v6, v34

    .line 1048
    const-string v4, "lanName"

    invoke-static {v4, v3}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v3

    const/16 v28, 0x1

    aput-object v3, v6, v28

    .line 1049
    invoke-static {v10, v2}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    const/16 v27, 0x2

    aput-object v2, v6, v27

    .line 1046
    invoke-static {v6}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v2

    invoke-interface {v15, v2}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    goto/16 :goto_22d5

    :cond_2374
    const/4 v0, 0x6

    .line 1055
    new-array v0, v0, [Lkotlin/Pair;

    const/16 v34, 0x0

    invoke-static/range {v34 .. v34}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2
    :try_end_237d
    .catch Ljava/lang/Exception; {:try_start_22a0 .. :try_end_237d} :catch_23e7

    move-object/from16 v3, p3

    :try_start_237f
    invoke-static {v3, v2}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    aput-object v2, v0, v34

    .line 1056
    invoke-static {v10, v13}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    const/16 v28, 0x1

    aput-object v2, v0, v28

    .line 1057
    const-string v2, "quality"
    :try_end_238f
    .catch Ljava/lang/Exception; {:try_start_237f .. :try_end_238f} :catch_23e5

    move-object/from16 v6, v44

    :try_start_2391
    invoke-static {v2, v6}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    const/16 v27, 0x2

    aput-object v2, v0, v27

    .line 1058
    const-string v2, "cookie"

    invoke-static {v2, v12}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    const/16 v24, 0x3

    aput-object v2, v0, v24

    .line 1059
    const-string v2, "resolutions"

    move-object v4, v8

    check-cast v4, Ljava/util/Collection;

    invoke-interface {v4}, Ljava/util/Collection;->isEmpty()Z

    move-result v4

    if-nez v4, :cond_23bd

    const/4 v11, 0x0

    invoke-interface {v8, v11}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Ljava/util/Map;

    const-string v5, "resolutions"

    invoke-interface {v4, v5}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    if-nez v4, :cond_23bf

    :cond_23bd
    move-object/from16 v4, v26

    :cond_23bf
    invoke-static {v2, v4}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    const/16 v23, 0x4

    aput-object v2, v0, v23

    .line 1060
    const-string v2, "subtitles"

    invoke-static {v2, v15}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    const/4 v4, 0x5

    aput-object v2, v0, v4

    .line 1054
    invoke-static {v0}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v0

    .line 1062
    new-instance v2, Lkotlin/Pair;

    invoke-static/range {v18 .. v18}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v4

    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->toJsonString(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    invoke-direct {v2, v4, v0}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V
    :try_end_23e1
    .catch Ljava/lang/Exception; {:try_start_2391 .. :try_end_23e1} :catch_23e3

    goto/16 :goto_ca4

    :catch_23e3
    move-exception v0

    goto :goto_23fb

    :catch_23e5
    move-exception v0

    goto :goto_23f9

    :catch_23e7
    move-exception v0

    move-object/from16 v3, p3

    goto :goto_23f9

    :catch_23eb
    move-exception v0

    move-object/from16 v1, p0

    goto :goto_23f0

    :catch_23ef
    move-exception v0

    :goto_23f0
    move-object/from16 v3, p3

    goto :goto_23f7

    :catch_23f3
    move-exception v0

    move-object/from16 v3, p3

    move-object v10, v7

    :goto_23f7
    move-object/from16 v9, v20

    :goto_23f9
    move-object/from16 v6, v44

    .line 1064
    :goto_23fb
    :try_start_23fb
    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v0

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "Stream fallback triggered: "

    invoke-virtual {v2, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v9, v0}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    const/4 v0, 0x5

    .line 1066
    new-array v0, v0, [Lkotlin/Pair;

    const/16 v34, 0x0

    invoke-static/range {v34 .. v34}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-static {v3, v2}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    aput-object v2, v0, v34

    move-object/from16 v2, v26

    .line 1067
    invoke-static {v10, v2}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v3

    const/16 v28, 0x1

    aput-object v3, v0, v28

    .line 1068
    const-string v3, "quality"

    invoke-static {v3, v6}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v3

    const/16 v27, 0x2

    aput-object v3, v0, v27

    .line 1069
    const-string v3, "cookie"

    invoke-static {v3, v2}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    const/4 v5, 0x3

    aput-object v2, v0, v5

    .line 1070
    const-string v2, "subtitles"

    .line 1071
    new-array v3, v5, [Lkotlin/Pair;

    const-string v4, "lan"

    const-string v5, "en"

    invoke-static {v4, v5}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v4

    const/16 v34, 0x0

    aput-object v4, v3, v34

    const-string v4, "lanName"

    const-string v5, "English"

    invoke-static {v4, v5}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v4

    const/16 v28, 0x1

    aput-object v4, v3, v28

    const-string v4, "https://raw.githubusercontent.com/andreyvit/subtitle-tools/master/test.srt"

    invoke-static {v10, v4}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v4

    const/16 v27, 0x2

    aput-object v4, v3, v27

    invoke-static {v3}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v3

    .line 1070
    invoke-static {v3}, Lkotlin/collections/CollectionsKt;->listOf(Ljava/lang/Object;)Ljava/util/List;

    move-result-object v3

    invoke-static {v2, v3}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    const/16 v23, 0x4

    aput-object v2, v0, v23

    .line 1065
    invoke-static {v0}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v0

    .line 1074
    new-instance v2, Lkotlin/Pair;

    invoke-static/range {v18 .. v18}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->toJsonString(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    invoke-direct {v2, v3, v0}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V

    goto/16 :goto_ca4

    :cond_248a
    move-object v14, v8

    move-object v9, v10

    move-object v3, v15

    move-object/from16 v15, v17

    move-object v8, v4

    .line 1083
    const-string v2, "/home/v2/get-list"

    move-object/from16 v10, p2

    invoke-static {v10, v2}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v2
    :try_end_2498
    .catch Ljava/lang/Exception; {:try_start_23fb .. :try_end_2498} :catch_279f

    if-eqz v2, :cond_24fc

    .line 1085
    :try_start_249a
    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->parseJsonToMap(Ljava/lang/String;)Ljava/util/Map;

    move-result-object v0

    .line 1086
    const-string v2, "categoryId"

    invoke-interface {v0, v2}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    if-eqz v2, :cond_24bd

    invoke-virtual {v2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v2

    if-eqz v2, :cond_24bd

    invoke-static {v2}, Lkotlin/text/StringsKt;->toDoubleOrNull(Ljava/lang/String;)Ljava/lang/Double;

    move-result-object v2

    if-eqz v2, :cond_24bd

    invoke-virtual {v2}, Ljava/lang/Double;->doubleValue()D

    move-result-wide v4

    double-to-int v2, v4

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2
    :try_end_24bb
    .catch Ljava/lang/Exception; {:try_start_249a .. :try_end_24bb} :catch_24eb

    move-object v5, v2

    goto :goto_24be

    :cond_24bd
    const/4 v5, 0x0

    .line 1087
    :goto_24be
    :try_start_24be
    invoke-interface {v0, v8}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    if-eqz v0, :cond_24e2

    invoke-virtual {v0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v0

    if-eqz v0, :cond_24e2

    invoke-static {v0}, Lkotlin/text/StringsKt;->toDoubleOrNull(Ljava/lang/String;)Ljava/lang/Double;

    move-result-object v0

    if-eqz v0, :cond_24e2

    invoke-virtual {v0}, Ljava/lang/Double;->doubleValue()D

    move-result-wide v6

    double-to-int v0, v6

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/Integer;->toString()Ljava/lang/String;

    move-result-object v0
    :try_end_24dd
    .catch Ljava/lang/Exception; {:try_start_24be .. :try_end_24dd} :catch_24e8

    if-nez v0, :cond_24e0

    goto :goto_24e2

    :cond_24e0
    move-object v2, v0

    goto :goto_24e3

    :cond_24e2
    :goto_24e2
    move-object v2, v12

    :goto_24e3
    :try_start_24e3
    sget-object v0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;
    :try_end_24e5
    .catch Ljava/lang/Exception; {:try_start_24e3 .. :try_end_24e5} :catch_24e6

    goto :goto_24f9

    :catch_24e6
    move-exception v0

    goto :goto_24ee

    :catch_24e8
    move-exception v0

    move-object v2, v12

    goto :goto_24ee

    :catch_24eb
    move-exception v0

    move-object v2, v12

    const/4 v5, 0x0

    .line 1089
    :goto_24ee
    :try_start_24ee
    const-string v4, "Error parsing get-list body"

    check-cast v0, Ljava/lang/Throwable;

    invoke-static {v9, v4, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    move-result v0

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    :goto_24f9
    const/4 v4, 0x0

    goto/16 :goto_25c2

    .line 1093
    :cond_24fc
    const-string v0, "/movies"

    invoke-static {v10, v0}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_250d

    const/16 v27, 0x2

    invoke-static/range {v27 .. v27}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    :goto_250a
    const/4 v4, 0x0

    goto/16 :goto_25ae

    .line 1094
    :cond_250d
    const-string v0, "/anime"

    invoke-static {v10, v0}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_251c

    const/16 v0, 0x8

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    goto :goto_250a

    .line 1095
    :cond_251c
    const-string v0, "/asian"

    invoke-static {v10, v0}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_252b

    const/16 v0, 0x12

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    goto :goto_250a

    .line 1096
    :cond_252b
    const-string v0, "/western"

    invoke-static {v10, v0}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_253a

    const/16 v0, 0x13

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    goto :goto_250a

    .line 1097
    :cond_253a
    const-string v0, "/short-tv"

    invoke-static {v10, v0}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_2549

    const/16 v0, 0xd

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    goto :goto_250a

    .line 1098
    :cond_2549
    const-string v0, "/kids"

    invoke-static {v10, v0}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_2558

    const/16 v0, 0x17

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    goto :goto_250a

    .line 1099
    :cond_2558
    const-string v0, "/education"

    invoke-static {v10, v0}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_2567

    const/16 v24, 0x3

    invoke-static/range {v24 .. v24}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    goto :goto_250a

    .line 1100
    :cond_2567
    const-string v0, "/music"

    invoke-static {v10, v0}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_2576

    const/16 v23, 0x4

    invoke-static/range {v23 .. v23}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    goto :goto_250a

    .line 1101
    :cond_2576
    const-string v0, "/nollywood"

    invoke-static {v10, v0}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_2585

    const/16 v0, 0x1c

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    goto :goto_250a

    .line 1102
    :cond_2585
    const-string v0, "/game"

    invoke-static {v10, v0}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_2595

    const/16 v0, 0xb

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    goto/16 :goto_250a

    .line 1103
    :cond_2595
    const-string v0, "/category/"

    const/4 v2, 0x2

    const/4 v4, 0x0

    const/4 v6, 0x0

    invoke-static {v10, v0, v6, v2, v4}, Lkotlin/text/StringsKt;->startsWith$default(Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_25ad

    const-string v0, "/category/"

    check-cast v0, Ljava/lang/CharSequence;

    invoke-static {v10, v0}, Lkotlin/text/StringsKt;->removePrefix(Ljava/lang/String;Ljava/lang/CharSequence;)Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lkotlin/text/StringsKt;->toIntOrNull(Ljava/lang/String;)Ljava/lang/Integer;

    move-result-object v5

    goto :goto_25ae

    :cond_25ad
    move-object v5, v4

    .line 1106
    :goto_25ae
    invoke-interface {v13, v8}, Ljava/util/Map;->containsKey(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_25bf

    .line 1107
    invoke-interface {v13, v8}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/String;

    if-nez v0, :cond_25bd

    move-object v0, v12

    :cond_25bd
    move-object v2, v0

    goto :goto_25c0

    :cond_25bf
    move-object v2, v12

    :goto_25c0
    sget-object v0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;
    :try_end_25c2
    .catch Ljava/lang/Exception; {:try_start_24ee .. :try_end_25c2} :catch_279f

    :goto_25c2
    move-object v0, v2

    move-object v13, v5

    if-eqz v13, :cond_2790

    .line 1114
    :try_start_25c6
    const-string v2, "GET"
    :try_end_25c8
    .catch Ljava/lang/Exception; {:try_start_25c6 .. :try_end_25c8} :catch_2742

    move-object v5, v3

    .line 1115
    :try_start_25c9
    const-string v3, "/wefeed-mobile-bff/tab-operating"

    const/4 v7, 0x3

    .line 1116
    new-array v6, v7, [Lkotlin/Pair;

    const-string v7, "tabId"

    invoke-virtual {v13}, Ljava/lang/Integer;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v7, v4}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v4

    const/16 v34, 0x0

    aput-object v4, v6, v34

    invoke-static {v8, v0}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v4

    const/16 v28, 0x1

    aput-object v4, v6, v28

    const-string v4, "pageSize"

    const-string v7, "24"

    invoke-static {v4, v7}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v4

    const/16 v27, 0x2

    aput-object v4, v6, v27

    invoke-static {v6}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v4
    :try_end_25f4
    .catch Ljava/lang/Exception; {:try_start_25c9 .. :try_end_25f4} :catch_273f

    const/16 v6, 0x8

    const/4 v7, 0x0

    move-object/from16 v17, v5

    const/4 v5, 0x0

    move-object/from16 v10, v17

    const/16 v35, 0x0

    .line 1113
    :try_start_25fe
    invoke-static/range {v1 .. v7}, Lcom/example/data/network/LocalMovieBoxServer;->makeOfficialRequest$default(Lcom/example/data/network/LocalMovieBoxServer;Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;

    move-result-object v2

    .line 1118
    invoke-direct {v1, v2}, Lcom/example/data/network/LocalMovieBoxServer;->parseJsonToMap(Ljava/lang/String;)Ljava/util/Map;

    move-result-object v2

    .line 1119
    invoke-interface {v2, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    instance-of v4, v3, Ljava/util/Map;

    if-eqz v4, :cond_2612

    move-object v5, v3

    check-cast v5, Ljava/util/Map;

    goto :goto_2614

    :cond_2612
    move-object/from16 v5, v35

    :goto_2614
    if-eqz v5, :cond_261c

    .line 1120
    invoke-interface {v5, v14}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    if-nez v3, :cond_2633

    :cond_261c
    if-eqz v5, :cond_2623

    invoke-interface {v5, v15}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    goto :goto_2625

    :cond_2623
    move-object/from16 v3, v35

    :goto_2625
    if-nez v3, :cond_2633

    if-eqz v5, :cond_2630

    const-string v3, "subjects"

    invoke-interface {v5, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    goto :goto_2634

    :cond_2630
    move-object/from16 v5, v35

    goto :goto_2634

    :cond_2633
    move-object v5, v3

    :goto_2634
    nop

    instance-of v3, v5, Ljava/util/List;

    if-eqz v3, :cond_263c

    check-cast v5, Ljava/util/List;

    goto :goto_263e

    :cond_263c
    move-object/from16 v5, v35

    :goto_263e
    if-nez v5, :cond_2654

    .line 1121
    invoke-interface {v2, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    instance-of v3, v2, Ljava/util/List;

    if-eqz v3, :cond_264c

    move-object v5, v2

    check-cast v5, Ljava/util/List;

    goto :goto_264e

    :cond_264c
    move-object/from16 v5, v35

    :goto_264e
    if-nez v5, :cond_2654

    .line 1122
    invoke-static {}, Lkotlin/collections/CollectionsKt;->emptyList()Ljava/util/List;

    move-result-object v5

    .line 1124
    :cond_2654
    invoke-direct {v1, v5}, Lcom/example/data/network/LocalMovieBoxServer;->formatTabSections(Ljava/util/List;)Ljava/util/List;

    move-result-object v2

    .line 1128
    invoke-interface {v2}, Ljava/util/List;->isEmpty()Z

    move-result v3

    if-eqz v3, :cond_26fd

    .line 1129
    new-instance v2, Ljava/lang/StringBuilder;

    move-object/from16 v3, v16

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2, v13}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, ", falling back to tabId=1"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v9, v2}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 1131
    const-string v2, "GET"

    .line 1132
    const-string v3, "/wefeed-mobile-bff/tab-operating"

    const/4 v5, 0x3

    .line 1133
    new-array v4, v5, [Lkotlin/Pair;

    const-string v5, "tabId"

    invoke-static {v5, v12}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v5

    const/16 v34, 0x0

    aput-object v5, v4, v34

    invoke-static {v8, v0}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    const/16 v28, 0x1

    aput-object v0, v4, v28

    const-string v0, "pageSize"

    const-string v5, "24"

    invoke-static {v0, v5}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    const/16 v27, 0x2

    aput-object v0, v4, v27

    invoke-static {v4}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v4

    const/16 v6, 0x8

    const/4 v7, 0x0

    const/4 v5, 0x0

    .line 1130
    invoke-static/range {v1 .. v7}, Lcom/example/data/network/LocalMovieBoxServer;->makeOfficialRequest$default(Lcom/example/data/network/LocalMovieBoxServer;Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    .line 1135
    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->parseJsonToMap(Ljava/lang/String;)Ljava/util/Map;

    move-result-object v0

    .line 1136
    invoke-interface {v0, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    instance-of v3, v2, Ljava/util/Map;

    if-eqz v3, :cond_26b7

    move-object v5, v2

    check-cast v5, Ljava/util/Map;

    goto :goto_26b9

    :cond_26b7
    move-object/from16 v5, v35

    :goto_26b9
    if-eqz v5, :cond_26c1

    .line 1137
    invoke-interface {v5, v14}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    if-nez v2, :cond_26d8

    :cond_26c1
    if-eqz v5, :cond_26c8

    invoke-interface {v5, v15}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    goto :goto_26ca

    :cond_26c8
    move-object/from16 v2, v35

    :goto_26ca
    if-nez v2, :cond_26d8

    if-eqz v5, :cond_26d5

    const-string v2, "subjects"

    invoke-interface {v5, v2}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    goto :goto_26d9

    :cond_26d5
    move-object/from16 v5, v35

    goto :goto_26d9

    :cond_26d8
    move-object v5, v2

    :goto_26d9
    nop

    instance-of v2, v5, Ljava/util/List;

    if-eqz v2, :cond_26e1

    check-cast v5, Ljava/util/List;

    goto :goto_26e3

    :cond_26e1
    move-object/from16 v5, v35

    :goto_26e3
    if-nez v5, :cond_26f9

    .line 1138
    invoke-interface {v0, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    instance-of v2, v0, Ljava/util/List;

    if-eqz v2, :cond_26f1

    move-object v5, v0

    check-cast v5, Ljava/util/List;

    goto :goto_26f3

    :cond_26f1
    move-object/from16 v5, v35

    :goto_26f3
    if-nez v5, :cond_26f9

    .line 1139
    invoke-static {}, Lkotlin/collections/CollectionsKt;->emptyList()Ljava/util/List;

    move-result-object v5

    .line 1140
    :cond_26f9
    invoke-direct {v1, v5}, Lcom/example/data/network/LocalMovieBoxServer;->formatTabSections(Ljava/util/List;)Ljava/util/List;

    move-result-object v2

    .line 1143
    :cond_26fd
    invoke-interface {v2}, Ljava/util/List;->isEmpty()Z

    move-result v0

    if-nez v0, :cond_2735

    const/4 v7, 0x2

    .line 1147
    new-array v0, v7, [Lkotlin/Pair;

    const/16 v34, 0x0

    invoke-static/range {v34 .. v34}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    invoke-static {v10, v3}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v3

    aput-object v3, v0, v34

    .line 1148
    invoke-static {v14, v2}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    invoke-static {v2}, Lkotlin/collections/MapsKt;->mapOf(Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v2

    invoke-static {v11, v2}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    const/16 v28, 0x1

    aput-object v2, v0, v28

    .line 1146
    invoke-static {v0}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v0

    .line 1150
    new-instance v2, Lkotlin/Pair;

    invoke-static/range {v18 .. v18}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->toJsonString(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    invoke-direct {v2, v3, v0}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V

    goto/16 :goto_ca4

    .line 1144
    :cond_2735
    new-instance v0, Ljava/lang/Exception;

    const-string v2, "Empty list from official API"

    invoke-direct {v0, v2}, Ljava/lang/Exception;-><init>(Ljava/lang/String;)V

    throw v0
    :try_end_273d
    .catch Ljava/lang/Exception; {:try_start_25fe .. :try_end_273d} :catch_273d

    :catch_273d
    move-exception v0

    goto :goto_2744

    :catch_273f
    move-exception v0

    move-object v10, v5

    goto :goto_2744

    :catch_2742
    move-exception v0

    move-object v10, v3

    .line 1152
    :goto_2744
    :try_start_2744
    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v0

    new-instance v2, Ljava/lang/StringBuilder;

    move-object/from16 v3, p1

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v9, v0}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 1153
    invoke-static {}, Lkotlin/collections/CollectionsKt;->emptyList()Ljava/util/List;

    move-result-object v0

    const/4 v2, 0x2

    .line 1155
    new-array v2, v2, [Lkotlin/Pair;

    const/16 v34, 0x0

    invoke-static/range {v34 .. v34}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    invoke-static {v10, v3}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v3

    aput-object v3, v2, v34

    .line 1156
    invoke-static {v14, v0}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    invoke-static {v0}, Lkotlin/collections/MapsKt;->mapOf(Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v0

    invoke-static {v11, v0}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    const/16 v28, 0x1

    aput-object v0, v2, v28

    .line 1154
    invoke-static {v2}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v0

    .line 1158
    new-instance v2, Lkotlin/Pair;

    invoke-static/range {v18 .. v18}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->toJsonString(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    invoke-direct {v2, v3, v0}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V

    goto/16 :goto_ca4

    .line 1161
    :cond_2790
    new-instance v0, Lkotlin/Pair;

    const/16 v1, 0x194

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    const-string v2, "{\"code\": 404, \"msg\": \"Not Found\"}"

    invoke-direct {v0, v1, v2}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V
    :try_end_279d
    .catch Ljava/lang/Exception; {:try_start_2744 .. :try_end_279d} :catch_279f

    goto/16 :goto_2c3a

    :catch_279f
    move-exception v0

    goto :goto_27a7

    :catch_27a1
    move-exception v0

    goto :goto_27a6

    :catch_27a3
    move-exception v0

    move-object/from16 v19, v9

    :goto_27a6
    move-object v9, v10

    :goto_27a7
    move-object v8, v9

    goto/16 :goto_2bfe

    :cond_27aa
    :goto_27aa
    move-object/from16 v35, v2

    move-object v8, v4

    move-object v3, v6

    move-object v4, v7

    move-object/from16 v19, v9

    move-object/from16 v20, v10

    move-object v10, v15

    move-object/from16 v9, v17

    move-object/from16 v2, v26

    move-object/from16 v15, p3

    .line 533
    :try_start_27ba
    const-string v0, "q"

    invoke-interface {v13, v0}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/String;
    :try_end_27c2
    .catch Ljava/lang/Exception; {:try_start_27ba .. :try_end_27c2} :catch_2bdd

    if-nez v0, :cond_27c5

    move-object v0, v2

    .line 535
    :cond_27c5
    :try_start_27c5
    move-object v2, v0

    check-cast v2, Ljava/lang/CharSequence;

    invoke-interface {v2}, Ljava/lang/CharSequence;->length()I

    move-result v2

    if-lez v2, :cond_28cf

    const/4 v5, 0x3

    .line 537
    new-array v2, v5, [Lkotlin/Pair;

    const-string v5, "keyword"

    invoke-static {v5, v0}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    const/16 v34, 0x0

    aput-object v0, v2, v34

    const/16 v28, 0x1

    .line 538
    invoke-static/range {v28 .. v28}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v0

    invoke-static {v8, v0}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    aput-object v0, v2, v28

    .line 539
    const-string v0, "pageSize"

    const/16 v5, 0x14

    invoke-static {v5}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    invoke-static {v0, v5}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    const/16 v27, 0x2

    aput-object v0, v2, v27

    .line 536
    invoke-static {v2}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v0

    .line 541
    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->toJsonString(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    .line 542
    const-string v2, "/wefeed-mobile-bff/subject-api/search"

    invoke-static {}, Lkotlin/collections/MapsKt;->emptyMap()Ljava/util/Map;

    move-result-object v5

    invoke-direct {v1, v14, v2, v5, v0}, Lcom/example/data/network/LocalMovieBoxServer;->makeOfficialRequest(Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 543
    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->parseJsonToMap(Ljava/lang/String;)Ljava/util/Map;

    move-result-object v0

    .line 544
    invoke-interface {v0, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    instance-of v5, v2, Ljava/util/Map;

    if-eqz v5, :cond_2819

    move-object v5, v2

    check-cast v5, Ljava/util/Map;

    goto :goto_281b

    :cond_2819
    move-object/from16 v5, v35

    :goto_281b
    if-eqz v5, :cond_2823

    .line 545
    invoke-interface {v5, v4}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    if-nez v2, :cond_283a

    :cond_2823
    if-eqz v5, :cond_282a

    invoke-interface {v5, v15}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    goto :goto_282c

    :cond_282a
    move-object/from16 v2, v35

    :goto_282c
    if-nez v2, :cond_283a

    if-eqz v5, :cond_2837

    const-string v2, "subjects"

    invoke-interface {v5, v2}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    goto :goto_283b

    :cond_2837
    move-object/from16 v5, v35

    goto :goto_283b

    :cond_283a
    move-object v5, v2

    :goto_283b
    nop

    instance-of v2, v5, Ljava/util/List;

    if-eqz v2, :cond_2843

    check-cast v5, Ljava/util/List;

    goto :goto_2845

    :cond_2843
    move-object/from16 v5, v35

    :goto_2845
    if-nez v5, :cond_285b

    .line 546
    invoke-interface {v0, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    instance-of v2, v0, Ljava/util/List;

    if-eqz v2, :cond_2853

    move-object v5, v0

    check-cast v5, Ljava/util/List;

    goto :goto_2855

    :cond_2853
    move-object/from16 v5, v35

    :goto_2855
    if-nez v5, :cond_285b

    .line 547
    invoke-static {}, Lkotlin/collections/CollectionsKt;->emptyList()Ljava/util/List;

    move-result-object v5

    .line 548
    :cond_285b
    check-cast v5, Ljava/lang/Iterable;

    .line 1828
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    check-cast v0, Ljava/util/Collection;

    .line 1837
    invoke-interface {v5}, Ljava/lang/Iterable;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :cond_2868
    :goto_2868
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v5

    if-eqz v5, :cond_28a3

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v5

    .line 1836
    check-cast v5, Ljava/util/Map;

    .line 549
    invoke-interface {v5, v9}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    if-eqz v6, :cond_2883

    invoke-virtual {v6}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v6

    if-nez v6, :cond_2881

    goto :goto_2883

    :cond_2881
    move-object v5, v6

    goto :goto_289d

    :cond_2883
    :goto_2883
    invoke-interface {v5, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    if-eqz v6, :cond_288e

    invoke-virtual {v6}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v5

    goto :goto_289d

    :cond_288e
    const-string v6, "keyword"

    invoke-interface {v5, v6}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    if-eqz v5, :cond_289b

    invoke-virtual {v5}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v5

    goto :goto_289d

    :cond_289b
    move-object/from16 v5, v35

    :goto_289d
    if-eqz v5, :cond_2868

    .line 1836
    invoke-interface {v0, v5}, Ljava/util/Collection;->add(Ljava/lang/Object;)Z

    goto :goto_2868

    .line 1840
    :cond_28a3
    check-cast v0, Ljava/util/List;

    const/4 v2, 0x2

    .line 552
    new-array v5, v2, [Lkotlin/Pair;

    const/16 v34, 0x0

    invoke-static/range {v34 .. v34}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-static {v10, v2}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    aput-object v2, v5, v34

    .line 553
    invoke-static {v11, v0}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    const/16 v28, 0x1

    aput-object v0, v5, v28

    .line 551
    invoke-static {v5}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v0

    .line 555
    new-instance v2, Lkotlin/Pair;

    invoke-static/range {v18 .. v18}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->toJsonString(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    invoke-direct {v2, v5, v0}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V

    goto/16 :goto_ca4

    .line 557
    :cond_28cf
    const-string v0, "/wefeed-mobile-bff/subject-api/trending/v2"

    invoke-static {}, Lkotlin/collections/MapsKt;->emptyMap()Ljava/util/Map;

    move-result-object v2

    const-string v5, "{}"

    invoke-direct {v1, v14, v0, v2, v5}, Lcom/example/data/network/LocalMovieBoxServer;->makeOfficialRequest(Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 558
    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->parseJsonToMap(Ljava/lang/String;)Ljava/util/Map;

    move-result-object v0

    .line 559
    invoke-interface {v0, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    instance-of v5, v2, Ljava/util/Map;

    if-eqz v5, :cond_28eb

    move-object v5, v2

    check-cast v5, Ljava/util/Map;

    goto :goto_28ed

    :cond_28eb
    move-object/from16 v5, v35

    :goto_28ed
    if-eqz v5, :cond_28f5

    .line 560
    invoke-interface {v5, v4}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    if-nez v2, :cond_290c

    :cond_28f5
    if-eqz v5, :cond_28fc

    invoke-interface {v5, v15}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    goto :goto_28fe

    :cond_28fc
    move-object/from16 v2, v35

    :goto_28fe
    if-nez v2, :cond_290c

    if-eqz v5, :cond_2909

    const-string v2, "subjects"

    invoke-interface {v5, v2}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    goto :goto_290d

    :cond_2909
    move-object/from16 v5, v35

    goto :goto_290d

    :cond_290c
    move-object v5, v2

    :goto_290d
    nop

    instance-of v2, v5, Ljava/util/List;

    if-eqz v2, :cond_2915

    check-cast v5, Ljava/util/List;

    goto :goto_2917

    :cond_2915
    move-object/from16 v5, v35

    :goto_2917
    if-nez v5, :cond_292d

    .line 561
    invoke-interface {v0, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    instance-of v2, v0, Ljava/util/List;

    if-eqz v2, :cond_2925

    move-object v5, v0

    check-cast v5, Ljava/util/List;

    goto :goto_2927

    :cond_2925
    move-object/from16 v5, v35

    :goto_2927
    if-nez v5, :cond_292d

    .line 562
    invoke-static {}, Lkotlin/collections/CollectionsKt;->emptyList()Ljava/util/List;

    move-result-object v5

    .line 563
    :cond_292d
    check-cast v5, Ljava/lang/Iterable;

    .line 1841
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    check-cast v0, Ljava/util/Collection;

    .line 1850
    invoke-interface {v5}, Ljava/lang/Iterable;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :cond_293a
    :goto_293a
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v5

    if-eqz v5, :cond_297d

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v5

    .line 1849
    check-cast v5, Ljava/util/Map;

    .line 564
    const-string v6, "subject"

    invoke-interface {v5, v6}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    instance-of v7, v6, Ljava/util/Map;

    if-eqz v7, :cond_2953

    check-cast v6, Ljava/util/Map;

    goto :goto_2955

    :cond_2953
    move-object/from16 v6, v35

    :goto_2955
    if-nez v6, :cond_2958

    move-object v6, v5

    .line 565
    :cond_2958
    invoke-interface {v6, v9}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v7

    if-nez v7, :cond_296e

    invoke-interface {v6, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v7

    if-nez v7, :cond_296e

    invoke-interface {v5, v9}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v7

    if-nez v7, :cond_296e

    invoke-interface {v5, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v7

    :cond_296e
    if-eqz v7, :cond_2975

    invoke-virtual {v7}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v5

    goto :goto_2977

    :cond_2975
    move-object/from16 v5, v35

    :goto_2977
    if-eqz v5, :cond_293a

    .line 1849
    invoke-interface {v0, v5}, Ljava/util/Collection;->add(Ljava/lang/Object;)Z

    goto :goto_293a

    .line 1853
    :cond_297d
    check-cast v0, Ljava/util/List;

    .line 1841
    check-cast v0, Ljava/lang/Iterable;

    const/16 v2, 0xa

    .line 566
    invoke-static {v0, v2}, Lkotlin/collections/CollectionsKt;->take(Ljava/lang/Iterable;I)Ljava/util/List;

    move-result-object v0

    .line 568
    invoke-interface {v0}, Ljava/util/List;->isEmpty()Z

    move-result v2

    if-eqz v2, :cond_2a52

    .line 569
    const-string v2, "GET"
    :try_end_298f
    .catch Ljava/lang/Exception; {:try_start_27c5 .. :try_end_298f} :catch_2aad

    move-object v13, v3

    :try_start_2990
    const-string v3, "/wefeed-mobile-bff/subject-api/search-rank/v2"

    const/4 v7, 0x2

    new-array v0, v7, [Lkotlin/Pair;

    invoke-static {v8, v12}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v5

    const/16 v34, 0x0

    aput-object v5, v0, v34

    const-string v5, "pageSize"

    const-string v6, "10"

    invoke-static {v5, v6}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v5

    const/16 v28, 0x1

    aput-object v5, v0, v28

    invoke-static {v0}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v0
    :try_end_29ad
    .catch Ljava/lang/Exception; {:try_start_2990 .. :try_end_29ad} :catch_2a50

    const/16 v6, 0x8

    const/4 v7, 0x0

    const/4 v5, 0x0

    move-object v12, v4

    move-object/from16 v8, v20

    move-object v4, v0

    :try_start_29b5
    invoke-static/range {v1 .. v7}, Lcom/example/data/network/LocalMovieBoxServer;->makeOfficialRequest$default(Lcom/example/data/network/LocalMovieBoxServer;Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    .line 570
    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->parseJsonToMap(Ljava/lang/String;)Ljava/util/Map;

    move-result-object v0

    .line 571
    invoke-interface {v0, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    instance-of v2, v0, Ljava/util/Map;

    if-eqz v2, :cond_29c9

    move-object v5, v0

    check-cast v5, Ljava/util/Map;

    goto :goto_29cb

    :cond_29c9
    move-object/from16 v5, v35

    :goto_29cb
    if-eqz v5, :cond_29d4

    .line 572
    const-string v0, "hot"

    invoke-interface {v5, v0}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    goto :goto_29d6

    :cond_29d4
    move-object/from16 v5, v35

    :goto_29d6
    instance-of v0, v5, Ljava/util/List;

    if-eqz v0, :cond_29dd

    check-cast v5, Ljava/util/List;

    goto :goto_29df

    :cond_29dd
    move-object/from16 v5, v35

    :goto_29df
    if-nez v5, :cond_29e5

    invoke-static {}, Lkotlin/collections/CollectionsKt;->emptyList()Ljava/util/List;

    move-result-object v5

    .line 573
    :cond_29e5
    invoke-static {v5}, Lkotlin/collections/CollectionsKt;->firstOrNull(Ljava/util/List;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/util/Map;

    if-nez v0, :cond_29f1

    invoke-static {}, Lkotlin/collections/MapsKt;->emptyMap()Ljava/util/Map;

    move-result-object v0

    .line 574
    :cond_29f1
    invoke-interface {v0, v15}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    if-nez v2, :cond_29fb

    invoke-interface {v0, v12}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    :cond_29fb
    instance-of v0, v2, Ljava/util/List;

    if-eqz v0, :cond_2a03

    move-object v5, v2

    check-cast v5, Ljava/util/List;

    goto :goto_2a05

    :cond_2a03
    move-object/from16 v5, v35

    :goto_2a05
    if-nez v5, :cond_2a0b

    invoke-static {}, Lkotlin/collections/CollectionsKt;->emptyList()Ljava/util/List;

    move-result-object v5

    .line 575
    :cond_2a0b
    check-cast v5, Ljava/lang/Iterable;

    .line 1854
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    check-cast v0, Ljava/util/Collection;

    .line 1863
    invoke-interface {v5}, Ljava/lang/Iterable;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :cond_2a18
    :goto_2a18
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_2a45

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    .line 1862
    check-cast v3, Ljava/util/Map;

    .line 576
    const-string v4, "keyword"

    invoke-interface {v3, v4}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    if-nez v4, :cond_2a36

    invoke-interface {v3, v9}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    if-nez v4, :cond_2a36

    invoke-interface {v3, v13}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    :cond_2a36
    if-eqz v4, :cond_2a3d

    invoke-virtual {v4}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v5

    goto :goto_2a3f

    :cond_2a3d
    move-object/from16 v5, v35

    :goto_2a3f
    if-eqz v5, :cond_2a18

    .line 1862
    invoke-interface {v0, v5}, Ljava/util/Collection;->add(Ljava/lang/Object;)Z

    goto :goto_2a18

    .line 1866
    :cond_2a45
    check-cast v0, Ljava/util/List;

    .line 1854
    check-cast v0, Ljava/lang/Iterable;

    const/16 v2, 0xa

    .line 577
    invoke-static {v0, v2}, Lkotlin/collections/CollectionsKt;->take(Ljava/lang/Iterable;I)Ljava/util/List;

    move-result-object v0

    goto :goto_2a56

    :catch_2a50
    move-exception v0

    goto :goto_2aaf

    :cond_2a52
    move-object v13, v3

    move-object v12, v4

    move-object/from16 v8, v20

    .line 579
    :goto_2a56
    invoke-interface {v0}, Ljava/util/List;->isEmpty()Z

    move-result v2

    if-eqz v2, :cond_2a81

    const/4 v0, 0x5

    .line 580
    new-array v0, v0, [Ljava/lang/String;

    const-string v2, "Action"

    const/16 v34, 0x0

    aput-object v2, v0, v34

    const-string v2, "Anime"

    const/16 v28, 0x1

    aput-object v2, v0, v28

    const-string v2, "Drama"

    const/16 v27, 0x2

    aput-object v2, v0, v27

    const-string v2, "Comedy"

    const/16 v24, 0x3

    aput-object v2, v0, v24

    const-string v2, "Sci-Fi"

    const/16 v23, 0x4

    aput-object v2, v0, v23

    invoke-static {v0}, Lkotlin/collections/CollectionsKt;->listOf([Ljava/lang/Object;)Ljava/util/List;

    move-result-object v0

    :cond_2a81
    const/4 v2, 0x2

    .line 584
    new-array v3, v2, [Lkotlin/Pair;

    const/16 v34, 0x0

    invoke-static/range {v34 .. v34}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-static {v10, v2}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    aput-object v2, v3, v34

    .line 585
    invoke-static {v11, v0}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    const/16 v28, 0x1

    aput-object v0, v3, v28

    .line 583
    invoke-static {v3}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v0

    .line 587
    new-instance v2, Lkotlin/Pair;

    invoke-static/range {v18 .. v18}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->toJsonString(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    invoke-direct {v2, v3, v0}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V
    :try_end_2aa9
    .catch Ljava/lang/Exception; {:try_start_29b5 .. :try_end_2aa9} :catch_2aab

    goto/16 :goto_ca4

    :catch_2aab
    move-exception v0

    goto :goto_2ab2

    :catch_2aad
    move-exception v0

    move-object v13, v3

    :goto_2aaf
    move-object v12, v4

    move-object/from16 v8, v20

    .line 590
    :goto_2ab2
    :try_start_2ab2
    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v0

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Search-suggestions fallback triggered: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v8, v0}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    const/4 v0, 0x5

    .line 591
    new-array v0, v0, [Ljava/lang/String;

    const-string v2, "Action"

    const/16 v34, 0x0

    aput-object v2, v0, v34

    const-string v2, "Anime"

    const/16 v28, 0x1

    aput-object v2, v0, v28

    const-string v2, "Drama"

    const/16 v27, 0x2

    aput-object v2, v0, v27

    const-string v2, "Comedy"

    const/16 v24, 0x3

    aput-object v2, v0, v24

    const-string v2, "Sci-Fi"

    const/16 v23, 0x4

    aput-object v2, v0, v23

    invoke-static {v0}, Lkotlin/collections/CollectionsKt;->listOf([Ljava/lang/Object;)Ljava/util/List;

    move-result-object v0
    :try_end_2af1
    .catch Ljava/lang/Exception; {:try_start_2ab2 .. :try_end_2af1} :catch_2bf8

    .line 593
    :try_start_2af1
    const-string v2, "/wefeed-mobile-bff/subject-api/trending/v2"

    invoke-static {}, Lkotlin/collections/MapsKt;->emptyMap()Ljava/util/Map;

    move-result-object v3

    const-string v4, "{}"

    invoke-direct {v1, v14, v2, v3, v4}, Lcom/example/data/network/LocalMovieBoxServer;->makeOfficialRequest(Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    .line 594
    invoke-direct {v1, v2}, Lcom/example/data/network/LocalMovieBoxServer;->parseJsonToMap(Ljava/lang/String;)Ljava/util/Map;

    move-result-object v2

    .line 595
    invoke-interface {v2, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    instance-of v4, v3, Ljava/util/Map;

    if-eqz v4, :cond_2b0d

    move-object v5, v3

    check-cast v5, Ljava/util/Map;

    goto :goto_2b0f

    :cond_2b0d
    move-object/from16 v5, v35

    :goto_2b0f
    if-eqz v5, :cond_2b17

    .line 596
    invoke-interface {v5, v12}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    if-nez v3, :cond_2b2e

    :cond_2b17
    if-eqz v5, :cond_2b1e

    invoke-interface {v5, v15}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    goto :goto_2b20

    :cond_2b1e
    move-object/from16 v3, v35

    :goto_2b20
    if-nez v3, :cond_2b2e

    if-eqz v5, :cond_2b2b

    const-string v3, "subjects"

    invoke-interface {v5, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    goto :goto_2b2f

    :cond_2b2b
    move-object/from16 v5, v35

    goto :goto_2b2f

    :cond_2b2e
    move-object v5, v3

    :goto_2b2f
    nop

    instance-of v3, v5, Ljava/util/List;

    if-eqz v3, :cond_2b37

    check-cast v5, Ljava/util/List;

    goto :goto_2b39

    :cond_2b37
    move-object/from16 v5, v35

    :goto_2b39
    if-nez v5, :cond_2b4f

    .line 597
    invoke-interface {v2, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    instance-of v3, v2, Ljava/util/List;

    if-eqz v3, :cond_2b47

    move-object v5, v2

    check-cast v5, Ljava/util/List;

    goto :goto_2b49

    :cond_2b47
    move-object/from16 v5, v35

    :goto_2b49
    if-nez v5, :cond_2b4f

    .line 598
    invoke-static {}, Lkotlin/collections/CollectionsKt;->emptyList()Ljava/util/List;

    move-result-object v5

    .line 599
    :cond_2b4f
    check-cast v5, Ljava/lang/Iterable;

    .line 1867
    new-instance v2, Ljava/util/ArrayList;

    invoke-direct {v2}, Ljava/util/ArrayList;-><init>()V

    check-cast v2, Ljava/util/Collection;

    .line 1876
    invoke-interface {v5}, Ljava/lang/Iterable;->iterator()Ljava/util/Iterator;

    move-result-object v3

    :cond_2b5c
    :goto_2b5c
    invoke-interface {v3}, Ljava/util/Iterator;->hasNext()Z

    move-result v4

    if-eqz v4, :cond_2b9f

    invoke-interface {v3}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v4

    .line 1875
    check-cast v4, Ljava/util/Map;

    .line 600
    const-string v5, "subject"

    invoke-interface {v4, v5}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    instance-of v6, v5, Ljava/util/Map;

    if-eqz v6, :cond_2b75

    check-cast v5, Ljava/util/Map;

    goto :goto_2b77

    :cond_2b75
    move-object/from16 v5, v35

    :goto_2b77
    if-nez v5, :cond_2b7a

    move-object v5, v4

    .line 601
    :cond_2b7a
    invoke-interface {v5, v9}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    if-nez v6, :cond_2b90

    invoke-interface {v5, v13}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    if-nez v6, :cond_2b90

    invoke-interface {v4, v9}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    if-nez v6, :cond_2b90

    invoke-interface {v4, v13}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    :cond_2b90
    if-eqz v6, :cond_2b97

    invoke-virtual {v6}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v5

    goto :goto_2b99

    :cond_2b97
    move-object/from16 v5, v35

    :goto_2b99
    if-eqz v5, :cond_2b5c

    .line 1875
    invoke-interface {v2, v5}, Ljava/util/Collection;->add(Ljava/lang/Object;)Z

    goto :goto_2b5c

    .line 1879
    :cond_2b9f
    check-cast v2, Ljava/util/List;

    .line 1867
    check-cast v2, Ljava/lang/Iterable;

    const/16 v3, 0xa

    .line 602
    invoke-static {v2, v3}, Lkotlin/collections/CollectionsKt;->take(Ljava/lang/Iterable;I)Ljava/util/List;

    move-result-object v2

    .line 603
    move-object v3, v2

    check-cast v3, Ljava/util/Collection;

    invoke-interface {v3}, Ljava/util/Collection;->isEmpty()Z

    move-result v3
    :try_end_2bb0
    .catch Ljava/lang/Exception; {:try_start_2af1 .. :try_end_2bb0} :catch_2bb3

    if-nez v3, :cond_2bb3

    move-object v0, v2

    :catch_2bb3
    :cond_2bb3
    const/4 v2, 0x2

    .line 609
    :try_start_2bb4
    new-array v2, v2, [Lkotlin/Pair;

    const/16 v34, 0x0

    invoke-static/range {v34 .. v34}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    invoke-static {v10, v3}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v3

    aput-object v3, v2, v34

    .line 610
    invoke-static {v11, v0}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    const/16 v28, 0x1

    aput-object v0, v2, v28

    .line 608
    invoke-static {v2}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v0

    .line 612
    new-instance v2, Lkotlin/Pair;

    invoke-static/range {v18 .. v18}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    invoke-direct {v1, v0}, Lcom/example/data/network/LocalMovieBoxServer;->toJsonString(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    invoke-direct {v2, v3, v0}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V

    goto/16 :goto_ca4

    :catch_2bdd
    move-exception v0

    move-object/from16 v8, v20

    goto :goto_2bfe

    :cond_2be1
    :goto_2be1
    move/from16 v18, v4

    move-object/from16 v19, v9

    move-object v8, v10

    .line 172
    new-instance v0, Lkotlin/Pair;

    invoke-static/range {v18 .. v18}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    sget-object v3, Lcom/example/data/network/DocsHtml;->INSTANCE:Lcom/example/data/network/DocsHtml;

    iget v1, v1, Lcom/example/data/network/LocalMovieBoxServer;->port:I

    invoke-virtual {v3, v1}, Lcom/example/data/network/DocsHtml;->getHtml(I)Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, v2, v1}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V
    :try_end_2bf7
    .catch Ljava/lang/Exception; {:try_start_2bb4 .. :try_end_2bf7} :catch_2bf8

    goto :goto_2c3a

    :catch_2bf8
    move-exception v0

    goto :goto_2bfe

    :catch_2bfa
    move-exception v0

    move-object/from16 v19, v9

    :goto_2bfd
    move-object v8, v10

    .line 1166
    :goto_2bfe
    new-instance v1, Ljava/lang/StringBuilder;

    const-string v2, "Error routing request "

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    move-object/from16 v3, p2

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    move-object v2, v0

    check-cast v2, Ljava/lang/Throwable;

    invoke-static {v8, v1, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 1167
    new-instance v1, Lkotlin/Pair;

    const/16 v2, 0x1f4

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-virtual {v0}, Ljava/lang/Exception;->getLocalizedMessage()Ljava/lang/String;

    move-result-object v0

    new-instance v3, Ljava/lang/StringBuilder;

    const-string v4, "{\"code\": 500, \"msg\": \""

    invoke-direct {v3, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    move-object/from16 v3, v19

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-direct {v1, v2, v0}, Lkotlin/Pair;-><init>(Ljava/lang/Object;Ljava/lang/Object;)V

    :goto_2c39
    move-object v0, v1

    :goto_2c3a
    return-object v0
.end method

.method static final routeRequest$lambda$15(Ljava/util/Map;Ljava/util/Map;)I
    .registers 5

    .line 940
    const-string v0, "url"

    invoke-interface {p0, v0}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    const-string v1, ""

    if-nez p0, :cond_b

    move-object p0, v1

    :cond_b
    invoke-virtual {p0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object p0

    sget-object v2, Ljava/util/Locale;->ROOT:Ljava/util/Locale;

    invoke-virtual {p0, v2}, Ljava/lang/String;->toLowerCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object p0

    const-string v2, "toLowerCase(...)"

    invoke-static {p0, v2}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V

    .line 941
    invoke-interface {p1, v0}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p1

    if-nez p1, :cond_21

    goto :goto_22

    :cond_21
    move-object v1, p1

    :goto_22
    invoke-virtual {v1}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object p1

    sget-object v0, Ljava/util/Locale;->ROOT:Ljava/util/Locale;

    invoke-virtual {p1, v0}, Ljava/lang/String;->toLowerCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object p1

    invoke-static {p1, v2}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V

    .line 950
    invoke-static {p1}, Lcom/example/data/network/LocalMovieBoxServer;->routeRequest$lambda$15$getScore(Ljava/lang/String;)I

    move-result p1

    invoke-static {p0}, Lcom/example/data/network/LocalMovieBoxServer;->routeRequest$lambda$15$getScore(Ljava/lang/String;)I

    move-result p0

    invoke-static {p1, p0}, Lkotlin/jvm/internal/Intrinsics;->compare(II)I

    move-result p0

    return p0
.end method

.method private static final routeRequest$lambda$15$getScore(Ljava/lang/String;)I
    .registers 7

    .line 944
    check-cast p0, Ljava/lang/CharSequence;

    const-string v0, "h265"

    check-cast v0, Ljava/lang/CharSequence;

    const/4 v1, 0x0

    const/4 v2, 0x2

    const/4 v3, 0x0

    invoke-static {p0, v0, v1, v2, v3}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v0

    const/4 v4, 0x1

    if-nez v0, :cond_27

    const-string v0, "x265"

    check-cast v0, Ljava/lang/CharSequence;

    invoke-static {p0, v0, v1, v2, v3}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_27

    const-string v0, "hev1"

    check-cast v0, Ljava/lang/CharSequence;

    invoke-static {p0, v0, v1, v2, v3}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_25

    goto :goto_27

    :cond_25
    move v0, v1

    goto :goto_28

    :cond_27
    :goto_27
    move v0, v4

    .line 945
    :goto_28
    const-string v5, ".m3u8"

    check-cast v5, Ljava/lang/CharSequence;

    invoke-static {p0, v5, v1, v2, v3}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_39

    if-eqz v0, :cond_36

    const/4 p0, 0x3

    return p0

    :cond_36
    const/16 p0, 0xa

    return p0

    .line 946
    :cond_39
    const-string v5, ".mpd"

    check-cast v5, Ljava/lang/CharSequence;

    invoke-static {p0, v5, v1, v2, v3}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_49

    if-eqz v0, :cond_46

    return v2

    :cond_46
    const/16 p0, 0x9

    return p0

    .line 947
    :cond_49
    const-string v5, ".mp4"

    check-cast v5, Ljava/lang/CharSequence;

    invoke-static {p0, v5, v1, v2, v3}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result p0

    if-eqz p0, :cond_59

    if-eqz v0, :cond_56

    return v4

    :cond_56
    const/16 p0, 0x8

    return p0

    :cond_59
    if-eqz v0, :cond_5c

    return v1

    :cond_5c
    const/4 p0, 0x5

    return p0
.end method

.method static final routeRequest$lambda$16(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Ljava/lang/Object;)I
    .registers 3

    .line 939
    invoke-interface {p0, p1, p2}, Lkotlin/jvm/functions/Function2;->invoke(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Ljava/lang/Number;

    invoke-virtual {p0}, Ljava/lang/Number;->intValue()I

    move-result p0

    return p0
.end method

.method private final sendResponse(Ljava/net/Socket;ILjava/lang/String;Ljava/lang/String;)V
    .registers 9

    .line 142
    invoke-virtual {p1}, Ljava/net/Socket;->getOutputStream()Ljava/io/OutputStream;

    move-result-object p0

    const-string p1, "getOutputStream(...)"

    invoke-static {p0, p1}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V

    const/4 p1, 0x2

    const/4 v0, 0x0

    .line 143
    const-string v1, "image/"

    const/4 v2, 0x0

    invoke-static {p4, v1, v2, p1, v0}, Lkotlin/text/StringsKt;->startsWith$default(Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Z

    move-result p1

    const-string v0, "UTF_8"

    const-string v1, "getBytes(...)"

    if-eqz p1, :cond_22

    .line 144
    sget-object p1, Lkotlin/text/Charsets;->ISO_8859_1:Ljava/nio/charset/Charset;

    invoke-virtual {p3, p1}, Ljava/lang/String;->getBytes(Ljava/nio/charset/Charset;)[B

    move-result-object p1

    invoke-static {p1, v1}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V

    goto :goto_2e

    .line 146
    :cond_22
    sget-object p1, Ljava/nio/charset/StandardCharsets;->UTF_8:Ljava/nio/charset/Charset;

    invoke-static {p1, v0}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V

    invoke-virtual {p3, p1}, Ljava/lang/String;->getBytes(Ljava/nio/charset/Charset;)[B

    move-result-object p1

    invoke-static {p1, v1}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V

    .line 150
    :goto_2e
    array-length p3, p1

    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "HTTP/1.1 "

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2, p2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object p2

    const-string v2, " OK\r\nContent-Type: "

    invoke-virtual {p2, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    invoke-virtual {p2, p4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    const-string p4, "\r\nContent-Length: "

    invoke-virtual {p2, p4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    invoke-virtual {p2, p3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object p2

    const-string p3, "\r\nConnection: close\r\nAccess-Control-Allow-Origin: *\r\n\r\n"

    invoke-virtual {p2, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    invoke-virtual {p2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p2

    .line 154
    sget-object p3, Ljava/nio/charset/StandardCharsets;->UTF_8:Ljava/nio/charset/Charset;

    invoke-static {p3, v0}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V

    invoke-virtual {p2, p3}, Ljava/lang/String;->getBytes(Ljava/nio/charset/Charset;)[B

    move-result-object p2

    invoke-static {p2, v1}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V

    invoke-virtual {p0, p2}, Ljava/io/OutputStream;->write([B)V

    .line 155
    invoke-virtual {p0, p1}, Ljava/io/OutputStream;->write([B)V

    .line 156
    invoke-virtual {p0}, Ljava/io/OutputStream;->flush()V

    return-void
.end method

.method static synthetic sendResponse$default(Lcom/example/data/network/LocalMovieBoxServer;Ljava/net/Socket;ILjava/lang/String;Ljava/lang/String;ILjava/lang/Object;)V
    .registers 7

    and-int/lit8 p5, p5, 0x8

    if-eqz p5, :cond_6

    .line 141
    const-string p4, "application/json; charset=utf-8"

    :cond_6
    invoke-direct {p0, p1, p2, p3, p4}, Lcom/example/data/network/LocalMovieBoxServer;->sendResponse(Ljava/net/Socket;ILjava/lang/String;Ljava/lang/String;)V

    return-void
.end method

.method private final toJsonElement(Ljava/lang/Object;)Ljava/lang/Object;
    .registers 5

    .line 1727
    instance-of v0, p1, Ljava/util/Map;

    if-eqz v0, :cond_36

    .line 1728
    new-instance v0, Lorg/json/JSONObject;

    invoke-direct {v0}, Lorg/json/JSONObject;-><init>()V

    .line 1729
    check-cast p1, Ljava/util/Map;

    .line 1943
    invoke-interface {p1}, Ljava/util/Map;->entrySet()Ljava/util/Set;

    move-result-object p1

    invoke-interface {p1}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object p1

    :cond_13
    :goto_13
    invoke-interface {p1}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_35

    invoke-interface {p1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/util/Map$Entry;

    invoke-interface {v1}, Ljava/util/Map$Entry;->getKey()Ljava/lang/Object;

    move-result-object v2

    invoke-interface {v1}, Ljava/util/Map$Entry;->getValue()Ljava/lang/Object;

    move-result-object v1

    if-eqz v2, :cond_13

    .line 1731
    invoke-virtual {v2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-direct {p0, v1}, Lcom/example/data/network/LocalMovieBoxServer;->toJsonElement(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    invoke-virtual {v0, v2, v1}, Lorg/json/JSONObject;->put(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;

    goto :goto_13

    :cond_35
    return-object v0

    .line 1736
    :cond_36
    instance-of v0, p1, Ljava/util/List;

    if-eqz v0, :cond_58

    .line 1737
    new-instance v0, Lorg/json/JSONArray;

    invoke-direct {v0}, Lorg/json/JSONArray;-><init>()V

    .line 1738
    check-cast p1, Ljava/lang/Iterable;

    .line 1945
    invoke-interface {p1}, Ljava/lang/Iterable;->iterator()Ljava/util/Iterator;

    move-result-object p1

    :goto_45
    invoke-interface {p1}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_57

    invoke-interface {p1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    .line 1739
    invoke-direct {p0, v1}, Lcom/example/data/network/LocalMovieBoxServer;->toJsonElement(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    invoke-virtual {v0, v1}, Lorg/json/JSONArray;->put(Ljava/lang/Object;)Lorg/json/JSONArray;

    goto :goto_45

    :cond_57
    return-object v0

    :cond_58
    if-nez p1, :cond_62

    .line 1743
    sget-object p0, Lorg/json/JSONObject;->NULL:Ljava/lang/Object;

    const-string p1, "NULL"

    invoke-static {p0, p1}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V

    return-object p0

    :cond_62
    return-object p1
.end method

.method private final toJsonString(Ljava/lang/Object;)Ljava/lang/String;
    .registers 5

    .line 1705
    instance-of v0, p1, Ljava/util/Map;

    if-eqz v0, :cond_3d

    .line 1706
    new-instance v0, Lorg/json/JSONObject;

    invoke-direct {v0}, Lorg/json/JSONObject;-><init>()V

    .line 1707
    check-cast p1, Ljava/util/Map;

    .line 1939
    invoke-interface {p1}, Ljava/util/Map;->entrySet()Ljava/util/Set;

    move-result-object p1

    invoke-interface {p1}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object p1

    :cond_13
    :goto_13
    invoke-interface {p1}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_35

    invoke-interface {p1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/util/Map$Entry;

    invoke-interface {v1}, Ljava/util/Map$Entry;->getKey()Ljava/lang/Object;

    move-result-object v2

    invoke-interface {v1}, Ljava/util/Map$Entry;->getValue()Ljava/lang/Object;

    move-result-object v1

    if-eqz v2, :cond_13

    .line 1709
    invoke-virtual {v2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-direct {p0, v1}, Lcom/example/data/network/LocalMovieBoxServer;->toJsonElement(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    invoke-virtual {v0, v2, v1}, Lorg/json/JSONObject;->put(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;

    goto :goto_13

    .line 1712
    :cond_35
    invoke-virtual {v0}, Lorg/json/JSONObject;->toString()Ljava/lang/String;

    move-result-object p0

    .line 1707
    invoke-static {p0}, Lkotlin/jvm/internal/Intrinsics;->checkNotNull(Ljava/lang/Object;)V

    return-object p0

    .line 1714
    :cond_3d
    instance-of v0, p1, Ljava/util/List;

    if-eqz v0, :cond_66

    .line 1715
    new-instance v0, Lorg/json/JSONArray;

    invoke-direct {v0}, Lorg/json/JSONArray;-><init>()V

    .line 1716
    check-cast p1, Ljava/lang/Iterable;

    .line 1941
    invoke-interface {p1}, Ljava/lang/Iterable;->iterator()Ljava/util/Iterator;

    move-result-object p1

    :goto_4c
    invoke-interface {p1}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_5e

    invoke-interface {p1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    .line 1717
    invoke-direct {p0, v1}, Lcom/example/data/network/LocalMovieBoxServer;->toJsonElement(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    invoke-virtual {v0, v1}, Lorg/json/JSONArray;->put(Ljava/lang/Object;)Lorg/json/JSONArray;

    goto :goto_4c

    .line 1719
    :cond_5e
    invoke-virtual {v0}, Lorg/json/JSONArray;->toString()Ljava/lang/String;

    move-result-object p0

    .line 1716
    invoke-static {p0}, Lkotlin/jvm/internal/Intrinsics;->checkNotNull(Ljava/lang/Object;)V

    return-object p0

    .line 1721
    :cond_66
    invoke-static {p1}, Lorg/json/JSONObject;->wrap(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    if-eqz p0, :cond_74

    invoke-virtual {p0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object p0

    if-nez p0, :cond_73

    goto :goto_74

    :cond_73
    return-object p0

    :cond_74
    :goto_74
    const-string p0, "null"

    return-object p0
.end method


# virtual methods
.method public final start()V
    .registers 8

    .line 47
    iget-boolean v0, p0, Lcom/example/data/network/LocalMovieBoxServer;->isRunning:Z

    if-eqz v0, :cond_5

    return-void

    :cond_5
    const/4 v0, 0x1

    .line 48
    iput-boolean v0, p0, Lcom/example/data/network/LocalMovieBoxServer;->isRunning:Z

    .line 49
    iget-object v1, p0, Lcom/example/data/network/LocalMovieBoxServer;->scope:Lkotlinx/coroutines/CoroutineScope;

    new-instance v0, Lcom/example/data/network/LocalMovieBoxServer$start$1;

    const/4 v2, 0x0

    invoke-direct {v0, p0, v2}, Lcom/example/data/network/LocalMovieBoxServer$start$1;-><init>(Lcom/example/data/network/LocalMovieBoxServer;Lkotlin/coroutines/Continuation;)V

    move-object v4, v0

    check-cast v4, Lkotlin/jvm/functions/Function2;

    const/4 v5, 0x3

    const/4 v6, 0x0

    const/4 v3, 0x0

    invoke-static/range {v1 .. v6}, Lkotlinx/coroutines/BuildersKt;->launch$default(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/CoroutineContext;Lkotlinx/coroutines/CoroutineStart;Lkotlin/jvm/functions/Function2;ILjava/lang/Object;)Lkotlinx/coroutines/Job;

    return-void
.end method

.method public final stop()V
    .registers 3

    const/4 v0, 0x0

    .line 72
    iput-boolean v0, p0, Lcom/example/data/network/LocalMovieBoxServer;->isRunning:Z

    .line 74
    :try_start_3
    iget-object v0, p0, Lcom/example/data/network/LocalMovieBoxServer;->serverSocket:Ljava/net/ServerSocket;

    if-eqz v0, :cond_a

    invoke-virtual {v0}, Ljava/net/ServerSocket;->close()V
    :try_end_a
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_a} :catch_a

    .line 78
    :catch_a
    :cond_a
    iget-object p0, p0, Lcom/example/data/network/LocalMovieBoxServer;->scope:Lkotlinx/coroutines/CoroutineScope;

    const/4 v0, 0x1

    const/4 v1, 0x0

    invoke-static {p0, v1, v0, v1}, Lkotlinx/coroutines/CoroutineScopeKt;->cancel$default(Lkotlinx/coroutines/CoroutineScope;Ljava/util/concurrent/CancellationException;ILjava/lang/Object;)V

    return-void
.end method
