.class public final Lcom/example/data/network/CookieInterceptor;
.super Ljava/lang/Object;
.source "MovieBoxApi.kt"

# interfaces
.implements Lokhttp3/Interceptor;


# annotations
.annotation system Ldalvik/annotation/SourceDebugExtension;
    value = "SMAP\nMovieBoxApi.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MovieBoxApi.kt\ncom/example/data/network/CookieInterceptor\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,644:1\n295#2,2:645\n*S KotlinDebug\n*F\n+ 1 MovieBoxApi.kt\ncom/example/data/network/CookieInterceptor\n*L\n608#1:645,2\n*E\n"
.end annotation

.annotation runtime Lkotlin/Metadata;
    d1 = {
        "\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0008\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0008\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0004\u0008\u0004\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0008\u001a\u00020\tH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"
    }
    d2 = {
        "Lcom/example/data/network/CookieInterceptor;",
        "Lokhttp3/Interceptor;",
        "prefs",
        "Lcom/example/data/preferences/PreferencesManager;",
        "<init>",
        "(Lcom/example/data/preferences/PreferencesManager;)V",
        "intercept",
        "Lokhttp3/Response;",
        "chain",
        "Lokhttp3/Interceptor$Chain;",
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
.field public static final $stable:I = 0x8


# instance fields
.field private final prefs:Lcom/example/data/preferences/PreferencesManager;


# direct methods
.method static constructor <clinit>()V
    .registers 0

    return-void
.end method

.method public constructor <init>(Lcom/example/data/preferences/PreferencesManager;)V
    .registers 3

    const-string v0, "prefs"

    invoke-static {p1, v0}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullParameter(Ljava/lang/Object;Ljava/lang/String;)V

    .line 598
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/example/data/network/CookieInterceptor;->prefs:Lcom/example/data/preferences/PreferencesManager;

    return-void
.end method


# virtual methods
.method public intercept(Lokhttp3/Interceptor$Chain;)Lokhttp3/Response;
    .registers 9

    const-string v0, "chain"

    invoke-static {p1, v0}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullParameter(Ljava/lang/Object;Ljava/lang/String;)V

    .line 600
    invoke-interface {p1}, Lokhttp3/Interceptor$Chain;->request()Lokhttp3/Request;

    move-result-object v0

    invoke-virtual {v0}, Lokhttp3/Request;->newBuilder()Lokhttp3/Request$Builder;

    move-result-object v0

    .line 601
    iget-object v1, p0, Lcom/example/data/network/CookieInterceptor;->prefs:Lcom/example/data/preferences/PreferencesManager;

    invoke-virtual {v1}, Lcom/example/data/preferences/PreferencesManager;->getSessionIdCookie()Ljava/lang/String;

    move-result-object v1

    .line 602
    move-object v2, v1

    check-cast v2, Ljava/lang/CharSequence;

    invoke-interface {v2}, Ljava/lang/CharSequence;->length()I

    move-result v2

    if-lez v2, :cond_21

    .line 603
    const-string v2, "Cookie"

    invoke-virtual {v0, v2, v1}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    .line 605
    :cond_21
    invoke-virtual {v0}, Lokhttp3/Request$Builder;->build()Lokhttp3/Request;

    move-result-object v0

    invoke-interface {p1, v0}, Lokhttp3/Interceptor$Chain;->proceed(Lokhttp3/Request;)Lokhttp3/Response;

    move-result-object p1

    .line 606
    const-string v0, "Set-Cookie"

    invoke-virtual {p1, v0}, Lokhttp3/Response;->headers(Ljava/lang/String;)Ljava/util/List;

    move-result-object v0

    .line 607
    move-object v1, v0

    check-cast v1, Ljava/util/Collection;

    invoke-interface {v1}, Ljava/util/Collection;->isEmpty()Z

    move-result v1

    if-nez v1, :cond_6b

    .line 608
    check-cast v0, Ljava/lang/Iterable;

    .line 645
    invoke-interface {v0}, Ljava/lang/Iterable;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :cond_3e
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    const/4 v2, 0x2

    const/4 v3, 0x0

    if-eqz v1, :cond_5b

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    move-object v4, v1

    check-cast v4, Ljava/lang/String;

    .line 608
    check-cast v4, Ljava/lang/CharSequence;

    const-string v5, "session_id"

    check-cast v5, Ljava/lang/CharSequence;

    const/4 v6, 0x0

    invoke-static {v4, v5, v6, v2, v3}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_3e

    goto :goto_5c

    :cond_5b
    move-object v1, v3

    :goto_5c
    check-cast v1, Ljava/lang/String;

    if-eqz v1, :cond_6b

    .line 610
    const-string v0, ";"

    invoke-static {v1, v0, v3, v2, v3}, Lkotlin/text/StringsKt;->substringBefore$default(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    .line 611
    iget-object p0, p0, Lcom/example/data/network/CookieInterceptor;->prefs:Lcom/example/data/preferences/PreferencesManager;

    invoke-virtual {p0, v0}, Lcom/example/data/preferences/PreferencesManager;->setSessionIdCookie(Ljava/lang/String;)V

    :cond_6b
    return-object p1
.end method
