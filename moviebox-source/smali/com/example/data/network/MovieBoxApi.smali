.class public interface abstract Lcom/example/data/network/MovieBoxApi;
.super Ljava/lang/Object;
.source "MovieBoxApi.kt"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/example/data/network/MovieBoxApi$DefaultImpls;
    }
.end annotation

.annotation runtime Lkotlin/Metadata;
    d1 = {
        "\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0008\n\u0002\u0008\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0008\u0002\n\u0002\u0018\u0002\n\u0002\u0008\u0004\n\u0002\u0018\u0002\n\u0002\u0008\u0008\n\u0002\u0018\u0002\n\u0002\u0008\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0008\u0002\n\u0002\u0018\u0002\n\u0002\u0008\u0002\n\u0002\u0018\u0002\n\u0002\u0008\u0004\n\u0002\u0018\u0002\n\u0002\u0008\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0008\u0002\n\u0002\u0018\u0002\n\u0002\u0008\u0003\n\u0002\u0018\u0002\n\u0002\u0008\u0002\u0008f\u0018\u00002\u00020\u0001J,\u0010\u0002\u001a\u00020\u00032\u0008\u0008\u0001\u0010\u0004\u001a\u00020\u00052\u0008\u0008\u0001\u0010\u0006\u001a\u00020\u00072\u0008\u0008\u0001\u0010\u0008\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\tJ)\u0010\n\u001a\u00020\u000b2\u0019\u0008\u0001\u0010\u000c\u001a\u0013\u0012\u0004\u0012\u00020\u0005\u0012\t\u0012\u00070\u0001\u00a2\u0006\u0002\u0008\u000e0\rH\u00a7@\u00a2\u0006\u0002\u0010\u000fJ\"\u0010\u0010\u001a\u00020\u00112\u0008\u0008\u0001\u0010\u0012\u001a\u00020\u00052\u0008\u0008\u0003\u0010\u0013\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0014JB\u0010\u0015\u001a\u00020\u00162\u0008\u0008\u0001\u0010\u0012\u001a\u00020\u00052\u0008\u0008\u0001\u0010\u0017\u001a\u00020\u00072\u0008\u0008\u0001\u0010\u0018\u001a\u00020\u00072\u0008\u0008\u0003\u0010\u0019\u001a\u00020\u00052\n\u0008\u0003\u0010\u001a\u001a\u0004\u0018\u00010\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u001bJ\"\u0010\u001c\u001a\u00020\u000b2\u0008\u0008\u0001\u0010\u0006\u001a\u00020\u00072\u0008\u0008\u0001\u0010\u0008\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u001dJ\u0018\u0010\u001e\u001a\u00020\u000b2\u0008\u0008\u0001\u0010\u000c\u001a\u00020\u001fH\u00a7@\u00a2\u0006\u0002\u0010 J\u0018\u0010!\u001a\u00020\"2\u0008\u0008\u0001\u0010\u000c\u001a\u00020#H\u00a7@\u00a2\u0006\u0002\u0010$J\u0018\u0010%\u001a\u00020\"2\u0008\u0008\u0001\u0010\u000c\u001a\u00020&H\u00a7@\u00a2\u0006\u0002\u0010\'J\u0018\u0010(\u001a\u00020\"2\u0008\u0008\u0001\u0010\u000c\u001a\u00020)H\u00a7@\u00a2\u0006\u0002\u0010*J\u000e\u0010+\u001a\u00020\"H\u00a7@\u00a2\u0006\u0002\u0010,J$\u0010-\u001a\u00020.2\u0014\u0008\u0001\u0010\u000c\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\rH\u00a7@\u00a2\u0006\u0002\u0010\u000fJ2\u0010/\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00010\r2\u0014\u0008\u0001\u0010\u000c\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\rH\u00a7@\u00a2\u0006\u0002\u0010\u000fJ\u001c\u00100\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00010\rH\u00a7@\u00a2\u0006\u0002\u0010,J\u000e\u00101\u001a\u000202H\u00a7@\u00a2\u0006\u0002\u0010,J\u0018\u00103\u001a\u00020.2\u0008\u0008\u0001\u0010\u000c\u001a\u000204H\u00a7@\u00a2\u0006\u0002\u00105J,\u00106\u001a\u0002072\u0008\u0008\u0001\u0010\u0006\u001a\u00020\u00072\u0008\u0008\u0001\u0010\u0008\u001a\u00020\u00072\u0008\u0008\u0003\u00108\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u00109J\u001a\u0010:\u001a\u00020;2\n\u0008\u0003\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u00a7@\u00a2\u0006\u0002\u0010<\u00a8\u0006=\u00c0\u0006\u0003"
    }
    d2 = {
        "Lcom/example/data/network/MovieBoxApi;",
        "",
        "search",
        "Lcom/example/data/model/SearchResponse;",
        "query",
        "",
        "page",
        "",
        "pageSize",
        "(Ljava/lang/String;IILkotlin/coroutines/Continuation;)Ljava/lang/Object;",
        "getDetailRec",
        "Lcom/example/data/model/HomeListResponse;",
        "request",
        "",
        "Lkotlin/jvm/JvmSuppressWildcards;",
        "(Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;",
        "getDetail",
        "Lcom/example/data/model/SubjectDetailResponse;",
        "subjectId",
        "host",
        "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;",
        "playInfo",
        "Lcom/example/data/model/PlayInfo;",
        "season",
        "episode",
        "quality",
        "resourceId",
        "(Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;",
        "getTrending",
        "(IILkotlin/coroutines/Continuation;)Ljava/lang/Object;",
        "getList",
        "Lcom/example/data/network/GetListRequest;",
        "(Lcom/example/data/network/GetListRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;",
        "login",
        "Lcom/example/data/model/AuthResponse;",
        "Lcom/example/data/model/LoginApiRequest;",
        "(Lcom/example/data/model/LoginApiRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;",
        "register",
        "Lcom/example/data/model/RegisterApiRequest;",
        "(Lcom/example/data/model/RegisterApiRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;",
        "requestOtp",
        "Lcom/example/data/model/OtpApiRequest;",
        "(Lcom/example/data/model/OtpApiRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;",
        "logout",
        "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;",
        "modifyProfile",
        "Lcom/example/data/model/CommonResponse;",
        "uploadAvatarLocal",
        "getStsToken",
        "getUserInfo",
        "Lcom/example/data/model/UserInfoResponse;",
        "toggleWatchlist",
        "Lcom/example/data/model/WantToSeeRequest;",
        "(Lcom/example/data/model/WantToSeeRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;",
        "getWatchlist",
        "Lcom/example/data/model/WatchlistResponse;",
        "seeType",
        "(IIILkotlin/coroutines/Continuation;)Ljava/lang/Object;",
        "getSearchSuggestions",
        "Lcom/example/data/model/SearchSuggestionsResponse;",
        "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;",
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


# direct methods
.method public static synthetic getDetail$default(Lcom/example/data/network/MovieBoxApi;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;ILjava/lang/Object;)Ljava/lang/Object;
    .registers 6

    if-nez p5, :cond_d

    and-int/lit8 p4, p4, 0x2

    if-eqz p4, :cond_8

    .line 42
    const-string p2, "api6.aoneroom.com"

    .line 39
    :cond_8
    invoke-interface {p0, p1, p2, p3}, Lcom/example/data/network/MovieBoxApi;->getDetail(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;

    move-result-object p0

    return-object p0

    :cond_d
    new-instance p0, Ljava/lang/UnsupportedOperationException;

    const-string p1, "Super calls with default arguments not supported in this target, function: getDetail"

    invoke-direct {p0, p1}, Ljava/lang/UnsupportedOperationException;-><init>(Ljava/lang/String;)V

    throw p0
.end method

.method public static synthetic getSearchSuggestions$default(Lcom/example/data/network/MovieBoxApi;Ljava/lang/String;Lkotlin/coroutines/Continuation;ILjava/lang/Object;)Ljava/lang/Object;
    .registers 5

    if-nez p4, :cond_c

    and-int/lit8 p3, p3, 0x1

    if-eqz p3, :cond_7

    const/4 p1, 0x0

    .line 111
    :cond_7
    invoke-interface {p0, p1, p2}, Lcom/example/data/network/MovieBoxApi;->getSearchSuggestions(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;

    move-result-object p0

    return-object p0

    :cond_c
    new-instance p0, Ljava/lang/UnsupportedOperationException;

    const-string p1, "Super calls with default arguments not supported in this target, function: getSearchSuggestions"

    invoke-direct {p0, p1}, Ljava/lang/UnsupportedOperationException;-><init>(Ljava/lang/String;)V

    throw p0
.end method

.method public static synthetic getWatchlist$default(Lcom/example/data/network/MovieBoxApi;IIILkotlin/coroutines/Continuation;ILjava/lang/Object;)Ljava/lang/Object;
    .registers 7

    if-nez p6, :cond_c

    and-int/lit8 p5, p5, 0x4

    if-eqz p5, :cond_7

    const/4 p3, 0x1

    .line 104
    :cond_7
    invoke-interface {p0, p1, p2, p3, p4}, Lcom/example/data/network/MovieBoxApi;->getWatchlist(IIILkotlin/coroutines/Continuation;)Ljava/lang/Object;

    move-result-object p0

    return-object p0

    :cond_c
    new-instance p0, Ljava/lang/UnsupportedOperationException;

    const-string p1, "Super calls with default arguments not supported in this target, function: getWatchlist"

    invoke-direct {p0, p1}, Ljava/lang/UnsupportedOperationException;-><init>(Ljava/lang/String;)V

    throw p0
.end method

.method public static synthetic playInfo$default(Lcom/example/data/network/MovieBoxApi;Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;ILjava/lang/Object;)Ljava/lang/Object;
    .registers 16

    if-nez p8, :cond_19

    and-int/lit8 p8, p7, 0x8

    if-eqz p8, :cond_8

    .line 50
    const-string p4, "1080p"

    :cond_8
    move-object v4, p4

    and-int/lit8 p4, p7, 0x10

    if-eqz p4, :cond_e

    const/4 p5, 0x0

    :cond_e
    move-object v0, p0

    move-object v1, p1

    move v2, p2

    move v3, p3

    move-object v5, p5

    move-object v6, p6

    .line 45
    invoke-interface/range {v0 .. v6}, Lcom/example/data/network/MovieBoxApi;->playInfo(Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;

    move-result-object p0

    return-object p0

    :cond_19
    new-instance p0, Ljava/lang/UnsupportedOperationException;

    const-string p1, "Super calls with default arguments not supported in this target, function: playInfo"

    invoke-direct {p0, p1}, Ljava/lang/UnsupportedOperationException;-><init>(Ljava/lang/String;)V

    throw p0
.end method


# virtual methods
.method public abstract getDetail(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;
    .param p1    # Ljava/lang/String;
        .annotation runtime Lretrofit2/http/Query;
            value = "subjectId"
        .end annotation
    .end param
    .param p2    # Ljava/lang/String;
        .annotation runtime Lretrofit2/http/Query;
            value = "host"
        .end annotation
    .end param
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/String;",
            "Ljava/lang/String;",
            "Lkotlin/coroutines/Continuation<",
            "-",
            "Lcom/example/data/model/SubjectDetailResponse;",
            ">;)",
            "Ljava/lang/Object;"
        }
    .end annotation

    .annotation runtime Lretrofit2/http/GET;
        value = "wefeed-mobile-bff/subject-api/get"
    .end annotation
.end method

.method public abstract getDetailRec(Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;
    .param p1    # Ljava/util/Map;
        .annotation runtime Lretrofit2/http/Body;
        .end annotation
    .end param
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/Map<",
            "Ljava/lang/String;",
            "Ljava/lang/Object;",
            ">;",
            "Lkotlin/coroutines/Continuation<",
            "-",
            "Lcom/example/data/model/HomeListResponse;",
            ">;)",
            "Ljava/lang/Object;"
        }
    .end annotation

    .annotation runtime Lretrofit2/http/POST;
        value = "wefeed-mobile-bff/subject-api/detail-rec"
    .end annotation
.end method

.method public abstract getList(Lcom/example/data/network/GetListRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;
    .param p1    # Lcom/example/data/network/GetListRequest;
        .annotation runtime Lretrofit2/http/Body;
        .end annotation
    .end param
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/example/data/network/GetListRequest;",
            "Lkotlin/coroutines/Continuation<",
            "-",
            "Lcom/example/data/model/HomeListResponse;",
            ">;)",
            "Ljava/lang/Object;"
        }
    .end annotation

    .annotation runtime Lretrofit2/http/POST;
        value = "home/v2/get-list"
    .end annotation
.end method

.method public abstract getSearchSuggestions(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;
    .param p1    # Ljava/lang/String;
        .annotation runtime Lretrofit2/http/Query;
            value = "q"
        .end annotation
    .end param
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/String;",
            "Lkotlin/coroutines/Continuation<",
            "-",
            "Lcom/example/data/model/SearchSuggestionsResponse;",
            ">;)",
            "Ljava/lang/Object;"
        }
    .end annotation

    .annotation runtime Lretrofit2/http/GET;
        value = "search-suggestions"
    .end annotation
.end method

.method public abstract getStsToken(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lkotlin/coroutines/Continuation<",
            "-",
            "Ljava/util/Map<",
            "Ljava/lang/String;",
            "+",
            "Ljava/lang/Object;",
            ">;>;)",
            "Ljava/lang/Object;"
        }
    .end annotation

    .annotation runtime Lretrofit2/http/GET;
        value = "wefeed-mobile-bff/upload/sts-token/v2"
    .end annotation
.end method

.method public abstract getTrending(IILkotlin/coroutines/Continuation;)Ljava/lang/Object;
    .param p1    # I
        .annotation runtime Lretrofit2/http/Query;
            value = "page"
        .end annotation
    .end param
    .param p2    # I
        .annotation runtime Lretrofit2/http/Query;
            value = "pageSize"
        .end annotation
    .end param
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(II",
            "Lkotlin/coroutines/Continuation<",
            "-",
            "Lcom/example/data/model/HomeListResponse;",
            ">;)",
            "Ljava/lang/Object;"
        }
    .end annotation

    .annotation runtime Lretrofit2/http/GET;
        value = "wefeed-mobile-bff/subject-api/trending/v2"
    .end annotation
.end method

.method public abstract getUserInfo(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lkotlin/coroutines/Continuation<",
            "-",
            "Lcom/example/data/model/UserInfoResponse;",
            ">;)",
            "Ljava/lang/Object;"
        }
    .end annotation

    .annotation runtime Lretrofit2/http/GET;
        value = "user-info"
    .end annotation
.end method

.method public abstract getWatchlist(IIILkotlin/coroutines/Continuation;)Ljava/lang/Object;
    .param p1    # I
        .annotation runtime Lretrofit2/http/Query;
            value = "page"
        .end annotation
    .end param
    .param p2    # I
        .annotation runtime Lretrofit2/http/Query;
            value = "pageSize"
        .end annotation
    .end param
    .param p3    # I
        .annotation runtime Lretrofit2/http/Query;
            value = "seeType"
        .end annotation
    .end param
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(III",
            "Lkotlin/coroutines/Continuation<",
            "-",
            "Lcom/example/data/model/WatchlistResponse;",
            ">;)",
            "Ljava/lang/Object;"
        }
    .end annotation

    .annotation runtime Lretrofit2/http/GET;
        value = "wefeed-mobile-bff/subject-api/see-list-v2"
    .end annotation
.end method

.method public abstract login(Lcom/example/data/model/LoginApiRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;
    .param p1    # Lcom/example/data/model/LoginApiRequest;
        .annotation runtime Lretrofit2/http/Body;
        .end annotation
    .end param
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/example/data/model/LoginApiRequest;",
            "Lkotlin/coroutines/Continuation<",
            "-",
            "Lcom/example/data/model/AuthResponse;",
            ">;)",
            "Ljava/lang/Object;"
        }
    .end annotation

    .annotation runtime Lretrofit2/http/POST;
        value = "login"
    .end annotation
.end method

.method public abstract logout(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lkotlin/coroutines/Continuation<",
            "-",
            "Lcom/example/data/model/AuthResponse;",
            ">;)",
            "Ljava/lang/Object;"
        }
    .end annotation

    .annotation runtime Lretrofit2/http/POST;
        value = "logout"
    .end annotation
.end method

.method public abstract modifyProfile(Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;
    .param p1    # Ljava/util/Map;
        .annotation runtime Lretrofit2/http/Body;
        .end annotation
    .end param
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/Map<",
            "Ljava/lang/String;",
            "Ljava/lang/String;",
            ">;",
            "Lkotlin/coroutines/Continuation<",
            "-",
            "Lcom/example/data/model/CommonResponse;",
            ">;)",
            "Ljava/lang/Object;"
        }
    .end annotation

    .annotation runtime Lretrofit2/http/POST;
        value = "modify"
    .end annotation
.end method

.method public abstract playInfo(Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;
    .param p1    # Ljava/lang/String;
        .annotation runtime Lretrofit2/http/Query;
            value = "subjectId"
        .end annotation
    .end param
    .param p2    # I
        .annotation runtime Lretrofit2/http/Query;
            value = "se"
        .end annotation
    .end param
    .param p3    # I
        .annotation runtime Lretrofit2/http/Query;
            value = "ep"
        .end annotation
    .end param
    .param p4    # Ljava/lang/String;
        .annotation runtime Lretrofit2/http/Query;
            value = "quality"
        .end annotation
    .end param
    .param p5    # Ljava/lang/String;
        .annotation runtime Lretrofit2/http/Query;
            value = "resourceId"
        .end annotation
    .end param
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/String;",
            "II",
            "Ljava/lang/String;",
            "Ljava/lang/String;",
            "Lkotlin/coroutines/Continuation<",
            "-",
            "Lcom/example/data/model/PlayInfo;",
            ">;)",
            "Ljava/lang/Object;"
        }
    .end annotation

    .annotation runtime Lretrofit2/http/GET;
        value = "wefeed-mobile-bff/subject-api/play-info"
    .end annotation
.end method

.method public abstract register(Lcom/example/data/model/RegisterApiRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;
    .param p1    # Lcom/example/data/model/RegisterApiRequest;
        .annotation runtime Lretrofit2/http/Body;
        .end annotation
    .end param
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/example/data/model/RegisterApiRequest;",
            "Lkotlin/coroutines/Continuation<",
            "-",
            "Lcom/example/data/model/AuthResponse;",
            ">;)",
            "Ljava/lang/Object;"
        }
    .end annotation

    .annotation runtime Lretrofit2/http/POST;
        value = "register"
    .end annotation
.end method

.method public abstract requestOtp(Lcom/example/data/model/OtpApiRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;
    .param p1    # Lcom/example/data/model/OtpApiRequest;
        .annotation runtime Lretrofit2/http/Body;
        .end annotation
    .end param
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/example/data/model/OtpApiRequest;",
            "Lkotlin/coroutines/Continuation<",
            "-",
            "Lcom/example/data/model/AuthResponse;",
            ">;)",
            "Ljava/lang/Object;"
        }
    .end annotation

    .annotation runtime Lretrofit2/http/POST;
        value = "request-otp"
    .end annotation
.end method

.method public abstract search(Ljava/lang/String;IILkotlin/coroutines/Continuation;)Ljava/lang/Object;
    .param p1    # Ljava/lang/String;
        .annotation runtime Lretrofit2/http/Query;
            value = "q"
        .end annotation
    .end param
    .param p2    # I
        .annotation runtime Lretrofit2/http/Query;
            value = "page"
        .end annotation
    .end param
    .param p3    # I
        .annotation runtime Lretrofit2/http/Query;
            value = "pageSize"
        .end annotation
    .end param
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/String;",
            "II",
            "Lkotlin/coroutines/Continuation<",
            "-",
            "Lcom/example/data/model/SearchResponse;",
            ">;)",
            "Ljava/lang/Object;"
        }
    .end annotation

    .annotation runtime Lretrofit2/http/GET;
        value = "wefeed-mobile-bff/subject-api/search"
    .end annotation
.end method

.method public abstract toggleWatchlist(Lcom/example/data/model/WantToSeeRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;
    .param p1    # Lcom/example/data/model/WantToSeeRequest;
        .annotation runtime Lretrofit2/http/Body;
        .end annotation
    .end param
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/example/data/model/WantToSeeRequest;",
            "Lkotlin/coroutines/Continuation<",
            "-",
            "Lcom/example/data/model/CommonResponse;",
            ">;)",
            "Ljava/lang/Object;"
        }
    .end annotation

    .annotation runtime Lretrofit2/http/POST;
        value = "wefeed-mobile-bff/subject-api/want-to-see"
    .end annotation
.end method

.method public abstract uploadAvatarLocal(Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;
    .param p1    # Ljava/util/Map;
        .annotation runtime Lretrofit2/http/Body;
        .end annotation
    .end param
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/Map<",
            "Ljava/lang/String;",
            "Ljava/lang/String;",
            ">;",
            "Lkotlin/coroutines/Continuation<",
            "-",
            "Ljava/util/Map<",
            "Ljava/lang/String;",
            "+",
            "Ljava/lang/Object;",
            ">;>;)",
            "Ljava/lang/Object;"
        }
    .end annotation

    .annotation runtime Lretrofit2/http/POST;
        value = "upload-avatar"
    .end annotation
.end method
