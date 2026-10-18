.class public final Lcom/example/data/network/MovieBoxSignatureInterceptor;
.super Ljava/lang/Object;
.source "MovieBoxApi.kt"

# interfaces
.implements Lokhttp3/Interceptor;


# annotations
.annotation runtime Lkotlin/Metadata;
    d1 = {
        "\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0008\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0008\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0004\u0008\u0004\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0008\u001a\u00020\tH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"
    }
    d2 = {
        "Lcom/example/data/network/MovieBoxSignatureInterceptor;",
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

    .line 130
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/example/data/network/MovieBoxSignatureInterceptor;->prefs:Lcom/example/data/preferences/PreferencesManager;

    return-void
.end method

.method static final intercept$lambda$2(Lokhttp3/HttpUrl;Ljava/lang/String;)Ljava/lang/CharSequence;
    .registers 3

    const-string v0, "name"

    invoke-static {p1, v0}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullParameter(Ljava/lang/Object;Ljava/lang/String;)V

    .line 288
    invoke-virtual {p0, p1}, Lokhttp3/HttpUrl;->queryParameter(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p0

    if-nez p0, :cond_d

    const-string p0, ""

    .line 289
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

.method static final intercept$lambda$3(B)Ljava/lang/CharSequence;
    .registers 2

    .line 309
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


# virtual methods
.method public intercept(Lokhttp3/Interceptor$Chain;)Lokhttp3/Response;
    .registers 32

    move-object/from16 v0, p0

    move-object/from16 v1, p1

    const-string v2, "chain"

    invoke-static {v1, v2}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullParameter(Ljava/lang/Object;Ljava/lang/String;)V

    .line 132
    invoke-interface {v1}, Lokhttp3/Interceptor$Chain;->request()Lokhttp3/Request;

    move-result-object v2

    .line 133
    iget-object v3, v0, Lcom/example/data/network/MovieBoxSignatureInterceptor;->prefs:Lcom/example/data/preferences/PreferencesManager;

    invoke-virtual {v3}, Lcom/example/data/preferences/PreferencesManager;->getMockHost()Ljava/lang/String;

    move-result-object v3

    .line 135
    move-object v4, v3

    check-cast v4, Ljava/lang/CharSequence;

    invoke-interface {v4}, Ljava/lang/CharSequence;->length()I

    move-result v4

    const-string v5, "Accept"

    const-string v6, "MovieBoxPro/16.2.1 (Android 12; Pixel 6)"

    const-string v7, "User-Agent"

    const-string v8, "?"

    const-string v9, "application/json"

    const-string v11, ""

    const/4 v13, 0x2

    if-lez v4, :cond_481

    .line 136
    invoke-virtual {v2}, Lokhttp3/Request;->url()Lokhttp3/HttpUrl;

    move-result-object v0

    .line 137
    invoke-virtual {v0}, Lokhttp3/HttpUrl;->pathSegments()Ljava/util/List;

    move-result-object v4

    .line 138
    move-object v14, v4

    check-cast v14, Ljava/lang/Iterable;

    const-string v4, "/"

    move-object v15, v4

    check-cast v15, Ljava/lang/CharSequence;

    const/16 v21, 0x3e

    const/16 v22, 0x0

    const/16 v16, 0x0

    const/16 v17, 0x0

    const/16 v18, 0x0

    const/16 v19, 0x0

    const/16 v20, 0x0

    invoke-static/range {v14 .. v22}, Lkotlin/collections/CollectionsKt;->joinToString$default(Ljava/lang/Iterable;Ljava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/CharSequence;ILjava/lang/CharSequence;Lkotlin/jvm/functions/Function1;ILjava/lang/Object;)Ljava/lang/String;

    move-result-object v14

    .line 140
    invoke-virtual {v0}, Lokhttp3/HttpUrl;->newBuilder()Lokhttp3/HttpUrl$Builder;

    move-result-object v15

    .line 141
    sget-object v10, Lokhttp3/HttpUrl;->Companion:Lokhttp3/HttpUrl$Companion;

    invoke-virtual {v10, v3}, Lokhttp3/HttpUrl$Companion;->parse(Ljava/lang/String;)Lokhttp3/HttpUrl;

    move-result-object v10

    if-eqz v10, :cond_6c

    .line 142
    invoke-virtual {v10}, Lokhttp3/HttpUrl;->scheme()Ljava/lang/String;

    move-result-object v12

    invoke-virtual {v15, v12}, Lokhttp3/HttpUrl$Builder;->scheme(Ljava/lang/String;)Lokhttp3/HttpUrl$Builder;

    .line 143
    invoke-virtual {v10}, Lokhttp3/HttpUrl;->host()Ljava/lang/String;

    move-result-object v12

    invoke-virtual {v15, v12}, Lokhttp3/HttpUrl$Builder;->host(Ljava/lang/String;)Lokhttp3/HttpUrl$Builder;

    .line 144
    invoke-virtual {v10}, Lokhttp3/HttpUrl;->port()I

    move-result v10

    invoke-virtual {v15, v10}, Lokhttp3/HttpUrl$Builder;->port(I)Lokhttp3/HttpUrl$Builder;

    .line 149
    :cond_6c
    move-object v10, v14

    check-cast v10, Ljava/lang/CharSequence;

    const-string v12, "wefeed-mobile-bff/subject-api/search"

    check-cast v12, Ljava/lang/CharSequence;

    move-object/from16 v18, v2

    const/4 v2, 0x0

    move-object/from16 v19, v11

    const/4 v11, 0x0

    invoke-static {v10, v12, v11, v13, v2}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v12

    const-string v2, "pageSize"

    const-string v11, "/search"

    const-string v13, "search"

    move/from16 v21, v12

    const-string v12, "/detail/"

    const-string v22, "1"

    move-object/from16 v23, v3

    const-string v3, "page"

    move-object/from16 v24, v8

    const-string v8, "q"

    if-nez v21, :cond_356

    move-object/from16 v21, v5

    move-object/from16 v25, v9

    const/4 v1, 0x0

    const/4 v5, 0x2

    const/4 v9, 0x0

    invoke-static {v14, v13, v1, v5, v9}, Lkotlin/text/StringsKt;->endsWith$default(Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Z

    move-result v17

    if-eqz v17, :cond_a2

    goto/16 :goto_35a

    .line 156
    :cond_a2
    const-string v17, "wefeed-mobile-bff/subject-api/get"

    move-object/from16 v26, v13

    move-object/from16 v13, v17

    check-cast v13, Ljava/lang/CharSequence;

    invoke-static {v10, v13, v1, v5, v9}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v13

    const-string v1, "subjectId"

    if-nez v13, :cond_331

    const-string v13, "get"

    move-object/from16 v27, v6

    const/4 v6, 0x0

    invoke-static {v14, v13, v6, v5, v9}, Lkotlin/text/StringsKt;->endsWith$default(Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Z

    move-result v13

    if-eqz v13, :cond_bf

    goto/16 :goto_333

    .line 161
    :cond_bf
    const-string v13, "wefeed-mobile-bff/subject-api/play-info"

    check-cast v13, Ljava/lang/CharSequence;

    invoke-static {v10, v13, v6, v5, v9}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v13

    if-nez v13, :cond_2c3

    const-string v13, "play-info"

    invoke-static {v14, v13, v6, v5, v9}, Lkotlin/text/StringsKt;->endsWith$default(Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Z

    move-result v13

    if-eqz v13, :cond_d3

    goto/16 :goto_2c3

    .line 182
    :cond_d3
    const-string v13, "wefeed-mobile-bff/subject-api/trending/v2"

    check-cast v13, Ljava/lang/CharSequence;

    invoke-static {v10, v13, v6, v5, v9}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v13

    if-nez v13, :cond_2b4

    const-string v13, "trending"

    check-cast v13, Ljava/lang/CharSequence;

    invoke-static {v10, v13, v6, v5, v9}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v13

    if-eqz v13, :cond_e9

    goto/16 :goto_2b4

    .line 186
    :cond_e9
    const-string v2, "wefeed-mobile-bff/subject-api/detail-rec"

    check-cast v2, Ljava/lang/CharSequence;

    invoke-static {v10, v2, v6, v5, v9}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v2

    if-nez v2, :cond_26b

    const-string v2, "detail-rec"

    invoke-static {v14, v2, v6, v5, v9}, Lkotlin/text/StringsKt;->endsWith$default(Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_fd

    goto/16 :goto_26b

    .line 197
    :cond_fd
    const-string v1, "home/v2/get-list"

    check-cast v1, Ljava/lang/CharSequence;

    invoke-static {v10, v1, v6, v5, v9}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_1b7

    const-string v1, "get-list"

    invoke-static {v14, v1, v6, v5, v9}, Lkotlin/text/StringsKt;->endsWith$default(Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_111

    goto/16 :goto_1b7

    .line 220
    :cond_111
    const-string v1, "login"

    invoke-static {v14, v1, v6, v5, v9}, Lkotlin/text/StringsKt;->endsWith$default(Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_1b0

    const-string v1, "/login"

    check-cast v1, Ljava/lang/CharSequence;

    invoke-static {v10, v1, v6, v5, v9}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_125

    goto/16 :goto_1b0

    .line 222
    :cond_125
    const-string v1, "register"

    invoke-static {v14, v1, v6, v5, v9}, Lkotlin/text/StringsKt;->endsWith$default(Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_1a9

    const-string v1, "/register"

    check-cast v1, Ljava/lang/CharSequence;

    invoke-static {v10, v1, v6, v5, v9}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_139

    goto/16 :goto_1a9

    .line 224
    :cond_139
    const-string v1, "request-otp"

    invoke-static {v14, v1, v6, v5, v9}, Lkotlin/text/StringsKt;->endsWith$default(Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_1a2

    const-string v1, "/request-otp"

    check-cast v1, Ljava/lang/CharSequence;

    invoke-static {v10, v1, v6, v5, v9}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_14c

    goto :goto_1a2

    .line 226
    :cond_14c
    const-string v1, "logout"

    invoke-static {v14, v1, v6, v5, v9}, Lkotlin/text/StringsKt;->endsWith$default(Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_19b

    const-string v1, "/logout"

    check-cast v1, Ljava/lang/CharSequence;

    invoke-static {v10, v1, v6, v5, v9}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_15f

    goto :goto_19b

    .line 228
    :cond_15f
    const-string v1, "user-info"

    invoke-static {v14, v1, v6, v5, v9}, Lkotlin/text/StringsKt;->endsWith$default(Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_194

    const-string v1, "/user-info"

    check-cast v1, Ljava/lang/CharSequence;

    invoke-static {v10, v1, v6, v5, v9}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_172

    goto :goto_194

    .line 230
    :cond_172
    const-string v1, "search-suggestions"

    check-cast v1, Ljava/lang/CharSequence;

    invoke-static {v10, v1, v6, v5, v9}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_184

    const-string v1, "search-suggestions"

    invoke-static {v14, v1, v6, v5, v9}, Lkotlin/text/StringsKt;->endsWith$default(Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_37d

    .line 231
    :cond_184
    invoke-virtual {v0, v8}, Lokhttp3/HttpUrl;->queryParameter(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 232
    const-string v1, "/search-suggestions"

    invoke-virtual {v15, v1}, Lokhttp3/HttpUrl$Builder;->encodedPath(Ljava/lang/String;)Lokhttp3/HttpUrl$Builder;

    if-eqz v0, :cond_37d

    .line 234
    invoke-virtual {v15, v8, v0}, Lokhttp3/HttpUrl$Builder;->setQueryParameter(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/HttpUrl$Builder;

    goto/16 :goto_37d

    .line 229
    :cond_194
    :goto_194
    const-string v0, "/user-info"

    invoke-virtual {v15, v0}, Lokhttp3/HttpUrl$Builder;->encodedPath(Ljava/lang/String;)Lokhttp3/HttpUrl$Builder;

    goto/16 :goto_37d

    .line 227
    :cond_19b
    :goto_19b
    const-string v0, "/logout"

    invoke-virtual {v15, v0}, Lokhttp3/HttpUrl$Builder;->encodedPath(Ljava/lang/String;)Lokhttp3/HttpUrl$Builder;

    goto/16 :goto_37d

    .line 225
    :cond_1a2
    :goto_1a2
    const-string v0, "/request-otp"

    invoke-virtual {v15, v0}, Lokhttp3/HttpUrl$Builder;->encodedPath(Ljava/lang/String;)Lokhttp3/HttpUrl$Builder;

    goto/16 :goto_37d

    .line 223
    :cond_1a9
    :goto_1a9
    const-string v0, "/register"

    invoke-virtual {v15, v0}, Lokhttp3/HttpUrl$Builder;->encodedPath(Ljava/lang/String;)Lokhttp3/HttpUrl$Builder;

    goto/16 :goto_37d

    .line 221
    :cond_1b0
    :goto_1b0
    const-string v0, "/login"

    invoke-virtual {v15, v0}, Lokhttp3/HttpUrl$Builder;->encodedPath(Ljava/lang/String;)Lokhttp3/HttpUrl$Builder;

    goto/16 :goto_37d

    .line 198
    :cond_1b7
    :goto_1b7
    invoke-virtual/range {v18 .. v18}, Lokhttp3/Request;->body()Lokhttp3/RequestBody;

    move-result-object v0

    if-eqz v0, :cond_1cd

    .line 200
    new-instance v1, Lokio/Buffer;

    invoke-direct {v1}, Lokio/Buffer;-><init>()V

    .line 201
    move-object v2, v1

    check-cast v2, Lokio/BufferedSink;

    invoke-virtual {v0, v2}, Lokhttp3/RequestBody;->writeTo(Lokio/BufferedSink;)V

    .line 202
    invoke-virtual {v1}, Lokio/Buffer;->readUtf8()Ljava/lang/String;

    move-result-object v0

    goto :goto_1cf

    :cond_1cd
    move-object/from16 v0, v19

    .line 205
    :goto_1cf
    new-instance v1, Lkotlin/text/Regex;

    const-string v2, "\"categoryId\"\\s*:\\s*(\\d+)"

    invoke-direct {v1, v2}, Lkotlin/text/Regex;-><init>(Ljava/lang/String;)V

    check-cast v0, Ljava/lang/CharSequence;

    const/4 v5, 0x2

    const/4 v6, 0x0

    const/4 v9, 0x0

    invoke-static {v1, v0, v6, v5, v9}, Lkotlin/text/Regex;->find$default(Lkotlin/text/Regex;Ljava/lang/CharSequence;IILjava/lang/Object;)Lkotlin/text/MatchResult;

    move-result-object v1

    if-eqz v1, :cond_1fb

    invoke-interface {v1}, Lkotlin/text/MatchResult;->getGroupValues()Ljava/util/List;

    move-result-object v1

    if-eqz v1, :cond_1fb

    const/4 v2, 0x1

    invoke-interface {v1, v2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/String;

    if-eqz v1, :cond_1fb

    invoke-static {v1}, Lkotlin/text/StringsKt;->toIntOrNull(Ljava/lang/String;)Ljava/lang/Integer;

    move-result-object v1

    if-eqz v1, :cond_1fb

    invoke-virtual {v1}, Ljava/lang/Integer;->intValue()I

    move-result v1

    goto :goto_1fc

    :cond_1fb
    const/4 v1, 0x2

    .line 206
    :goto_1fc
    new-instance v2, Lkotlin/text/Regex;

    const-string v5, "\"page\"\\s*:\\s*(\\d+)"

    invoke-direct {v2, v5}, Lkotlin/text/Regex;-><init>(Ljava/lang/String;)V

    const/4 v5, 0x2

    const/4 v6, 0x0

    const/4 v9, 0x0

    invoke-static {v2, v0, v6, v5, v9}, Lkotlin/text/Regex;->find$default(Lkotlin/text/Regex;Ljava/lang/CharSequence;IILjava/lang/Object;)Lkotlin/text/MatchResult;

    move-result-object v0

    if-eqz v0, :cond_226

    invoke-interface {v0}, Lkotlin/text/MatchResult;->getGroupValues()Ljava/util/List;

    move-result-object v0

    if-eqz v0, :cond_226

    const/4 v2, 0x1

    invoke-interface {v0, v2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/String;

    if-eqz v0, :cond_226

    invoke-static {v0}, Lkotlin/text/StringsKt;->toIntOrNull(Ljava/lang/String;)Ljava/lang/Integer;

    move-result-object v0

    if-eqz v0, :cond_226

    invoke-virtual {v0}, Ljava/lang/Integer;->intValue()I

    move-result v2

    goto :goto_227

    :cond_226
    const/4 v2, 0x1

    :goto_227
    const/4 v5, 0x2

    if-eq v1, v5, :cond_24e

    const/4 v0, 0x5

    if-eq v1, v0, :cond_24b

    const/16 v0, 0x8

    if-eq v1, v0, :cond_248

    const/16 v0, 0x12

    if-eq v1, v0, :cond_245

    .line 213
    new-instance v0, Ljava/lang/StringBuilder;

    const-string v5, "category/"

    invoke-direct {v0, v5}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    goto :goto_250

    .line 211
    :cond_245
    const-string v0, "asian"

    goto :goto_250

    .line 210
    :cond_248
    const-string v0, "anime"

    goto :goto_250

    .line 212
    :cond_24b
    const-string v0, "western"

    goto :goto_250

    .line 209
    :cond_24e
    const-string v0, "movies"

    .line 216
    :goto_250
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v15, v0}, Lokhttp3/HttpUrl$Builder;->encodedPath(Ljava/lang/String;)Lokhttp3/HttpUrl$Builder;

    move-result-object v0

    .line 217
    invoke-static {v2}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v3, v1}, Lokhttp3/HttpUrl$Builder;->setQueryParameter(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/HttpUrl$Builder;

    const/4 v10, 0x1

    goto/16 :goto_37e

    .line 187
    :cond_26b
    :goto_26b
    invoke-virtual/range {v18 .. v18}, Lokhttp3/Request;->body()Lokhttp3/RequestBody;

    move-result-object v0

    if-eqz v0, :cond_281

    .line 189
    new-instance v2, Lokio/Buffer;

    invoke-direct {v2}, Lokio/Buffer;-><init>()V

    .line 190
    move-object v3, v2

    check-cast v3, Lokio/BufferedSink;

    invoke-virtual {v0, v3}, Lokhttp3/RequestBody;->writeTo(Lokio/BufferedSink;)V

    .line 191
    invoke-virtual {v2}, Lokio/Buffer;->readUtf8()Ljava/lang/String;

    move-result-object v0

    goto :goto_283

    :cond_281
    move-object/from16 v0, v19

    .line 193
    :goto_283
    new-instance v2, Lkotlin/text/Regex;

    const-string v3, "\"subjectId\"\\s*:\\s*\"?(\\d+)\"?"

    invoke-direct {v2, v3}, Lkotlin/text/Regex;-><init>(Ljava/lang/String;)V

    check-cast v0, Ljava/lang/CharSequence;

    const/4 v5, 0x2

    const/4 v6, 0x0

    const/4 v9, 0x0

    invoke-static {v2, v0, v6, v5, v9}, Lkotlin/text/Regex;->find$default(Lkotlin/text/Regex;Ljava/lang/CharSequence;IILjava/lang/Object;)Lkotlin/text/MatchResult;

    move-result-object v0

    if-eqz v0, :cond_2a5

    invoke-interface {v0}, Lkotlin/text/MatchResult;->getGroupValues()Ljava/util/List;

    move-result-object v0

    if-eqz v0, :cond_2a5

    const/4 v2, 0x1

    invoke-interface {v0, v2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/String;

    if-nez v0, :cond_2a8

    goto :goto_2a6

    :cond_2a5
    const/4 v2, 0x1

    :goto_2a6
    move-object/from16 v0, v19

    .line 194
    :cond_2a8
    const-string v3, "/detail-rec"

    invoke-virtual {v15, v3}, Lokhttp3/HttpUrl$Builder;->encodedPath(Ljava/lang/String;)Lokhttp3/HttpUrl$Builder;

    move-result-object v3

    .line 195
    invoke-virtual {v3, v1, v0}, Lokhttp3/HttpUrl$Builder;->setQueryParameter(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/HttpUrl$Builder;

    move v10, v2

    goto/16 :goto_37e

    .line 183
    :cond_2b4
    :goto_2b4
    const-string v0, "/trending"

    invoke-virtual {v15, v0}, Lokhttp3/HttpUrl$Builder;->encodedPath(Ljava/lang/String;)Lokhttp3/HttpUrl$Builder;

    move-result-object v0

    .line 184
    invoke-virtual {v0, v3}, Lokhttp3/HttpUrl$Builder;->removeAllQueryParameters(Ljava/lang/String;)Lokhttp3/HttpUrl$Builder;

    move-result-object v0

    .line 185
    invoke-virtual {v0, v2}, Lokhttp3/HttpUrl$Builder;->removeAllQueryParameters(Ljava/lang/String;)Lokhttp3/HttpUrl$Builder;

    goto/16 :goto_37d

    .line 162
    :cond_2c3
    :goto_2c3
    invoke-virtual {v0, v1}, Lokhttp3/HttpUrl;->queryParameter(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    if-nez v2, :cond_2cb

    move-object/from16 v2, v19

    .line 163
    :cond_2cb
    const-string v3, "se"

    invoke-virtual {v0, v3}, Lokhttp3/HttpUrl;->queryParameter(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v5

    if-nez v5, :cond_2d5

    move-object/from16 v5, v22

    .line 164
    :cond_2d5
    const-string v6, "ep"

    invoke-virtual {v0, v6}, Lokhttp3/HttpUrl;->queryParameter(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v6

    if-nez v6, :cond_2df

    move-object/from16 v6, v22

    .line 165
    :cond_2df
    const-string v8, "quality"

    invoke-virtual {v0, v8}, Lokhttp3/HttpUrl;->queryParameter(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v9

    if-nez v9, :cond_2e9

    const-string v9, "720p"

    .line 166
    :cond_2e9
    const-string v10, "resourceId"

    invoke-virtual {v0, v10}, Lokhttp3/HttpUrl;->queryParameter(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 168
    new-instance v10, Ljava/lang/StringBuilder;

    const-string v13, "/stream/"

    invoke-direct {v10, v13}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v10, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v15, v2}, Lokhttp3/HttpUrl$Builder;->encodedPath(Ljava/lang/String;)Lokhttp3/HttpUrl$Builder;

    move-result-object v2

    .line 169
    const-string v10, "season"

    invoke-virtual {v2, v10, v5}, Lokhttp3/HttpUrl$Builder;->setQueryParameter(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/HttpUrl$Builder;

    move-result-object v2

    .line 170
    const-string v5, "episode"

    invoke-virtual {v2, v5, v6}, Lokhttp3/HttpUrl$Builder;->setQueryParameter(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/HttpUrl$Builder;

    move-result-object v2

    .line 171
    invoke-virtual {v2, v8, v9}, Lokhttp3/HttpUrl$Builder;->setQueryParameter(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/HttpUrl$Builder;

    move-result-object v2

    if-eqz v0, :cond_319

    .line 174
    const-string v5, "resource_id"

    invoke-virtual {v2, v5, v0}, Lokhttp3/HttpUrl$Builder;->setQueryParameter(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/HttpUrl$Builder;

    .line 177
    :cond_319
    invoke-virtual {v2, v1}, Lokhttp3/HttpUrl$Builder;->removeAllQueryParameters(Ljava/lang/String;)Lokhttp3/HttpUrl$Builder;

    move-result-object v0

    .line 178
    invoke-virtual {v0, v3}, Lokhttp3/HttpUrl$Builder;->removeAllQueryParameters(Ljava/lang/String;)Lokhttp3/HttpUrl$Builder;

    move-result-object v0

    .line 179
    const-string v1, "ep"

    invoke-virtual {v0, v1}, Lokhttp3/HttpUrl$Builder;->removeAllQueryParameters(Ljava/lang/String;)Lokhttp3/HttpUrl$Builder;

    move-result-object v0

    .line 180
    invoke-virtual {v0, v8}, Lokhttp3/HttpUrl$Builder;->removeAllQueryParameters(Ljava/lang/String;)Lokhttp3/HttpUrl$Builder;

    move-result-object v0

    .line 181
    const-string v1, "resourceId"

    invoke-virtual {v0, v1}, Lokhttp3/HttpUrl$Builder;->removeAllQueryParameters(Ljava/lang/String;)Lokhttp3/HttpUrl$Builder;

    goto :goto_37d

    :cond_331
    move-object/from16 v27, v6

    .line 157
    :goto_333
    invoke-virtual {v0, v1}, Lokhttp3/HttpUrl;->queryParameter(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    if-nez v0, :cond_33b

    move-object/from16 v0, v19

    .line 158
    :cond_33b
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2, v12}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v15, v0}, Lokhttp3/HttpUrl$Builder;->encodedPath(Ljava/lang/String;)Lokhttp3/HttpUrl$Builder;

    move-result-object v0

    .line 159
    invoke-virtual {v0, v1}, Lokhttp3/HttpUrl$Builder;->removeAllQueryParameters(Ljava/lang/String;)Lokhttp3/HttpUrl$Builder;

    move-result-object v0

    .line 160
    const-string v1, "host"

    invoke-virtual {v0, v1}, Lokhttp3/HttpUrl$Builder;->removeAllQueryParameters(Ljava/lang/String;)Lokhttp3/HttpUrl$Builder;

    goto :goto_37d

    :cond_356
    move-object/from16 v21, v5

    move-object/from16 v25, v9

    :goto_35a
    move-object/from16 v27, v6

    move-object/from16 v26, v13

    .line 150
    invoke-virtual {v0, v8}, Lokhttp3/HttpUrl;->queryParameter(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    if-nez v1, :cond_366

    move-object/from16 v1, v19

    .line 151
    :cond_366
    invoke-virtual {v0, v3}, Lokhttp3/HttpUrl;->queryParameter(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    if-nez v0, :cond_36e

    move-object/from16 v0, v22

    .line 152
    :cond_36e
    invoke-virtual {v15, v11}, Lokhttp3/HttpUrl$Builder;->encodedPath(Ljava/lang/String;)Lokhttp3/HttpUrl$Builder;

    move-result-object v5

    .line 153
    invoke-virtual {v5, v8, v1}, Lokhttp3/HttpUrl$Builder;->setQueryParameter(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/HttpUrl$Builder;

    move-result-object v1

    .line 154
    invoke-virtual {v1, v3, v0}, Lokhttp3/HttpUrl$Builder;->setQueryParameter(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/HttpUrl$Builder;

    move-result-object v0

    .line 155
    invoke-virtual {v0, v2}, Lokhttp3/HttpUrl$Builder;->removeAllQueryParameters(Ljava/lang/String;)Lokhttp3/HttpUrl$Builder;

    :cond_37d
    :goto_37d
    const/4 v10, 0x0

    .line 238
    :goto_37e
    invoke-virtual {v15}, Lokhttp3/HttpUrl$Builder;->build()Lokhttp3/HttpUrl;

    move-result-object v0

    .line 239
    invoke-virtual/range {v18 .. v18}, Lokhttp3/Request;->newBuilder()Lokhttp3/Request$Builder;

    move-result-object v1

    .line 240
    invoke-virtual {v1, v0}, Lokhttp3/Request$Builder;->url(Lokhttp3/HttpUrl;)Lokhttp3/Request$Builder;

    move-result-object v1

    move-object/from16 v3, v27

    .line 241
    invoke-virtual {v1, v7, v3}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v1

    move-object/from16 v5, v21

    move-object/from16 v6, v25

    .line 242
    invoke-virtual {v1, v5, v6}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v1

    if-eqz v10, :cond_3a0

    .line 245
    const-string v2, "GET"

    const/4 v9, 0x0

    invoke-virtual {v1, v2, v9}, Lokhttp3/Request$Builder;->method(Ljava/lang/String;Lokhttp3/RequestBody;)Lokhttp3/Request$Builder;

    .line 248
    :cond_3a0
    invoke-virtual {v1}, Lokhttp3/Request$Builder;->build()Lokhttp3/Request;

    move-result-object v1

    move-object/from16 v8, p1

    invoke-interface {v8, v1}, Lokhttp3/Interceptor$Chain;->proceed(Lokhttp3/Request;)Lokhttp3/Response;

    move-result-object v1

    .line 249
    invoke-virtual {v1}, Lokhttp3/Response;->isSuccessful()Z

    move-result v2

    if-eqz v2, :cond_480

    .line 250
    invoke-virtual {v1}, Lokhttp3/Response;->body()Lokhttp3/ResponseBody;

    move-result-object v2

    if-eqz v2, :cond_480

    .line 252
    invoke-virtual {v2}, Lokhttp3/ResponseBody;->string()Ljava/lang/String;

    move-result-object v3

    .line 254
    :try_start_3ba
    invoke-virtual {v0}, Lokhttp3/HttpUrl;->encodedPath()Ljava/lang/String;

    move-result-object v0

    .line 255
    move-object v5, v0

    check-cast v5, Ljava/lang/CharSequence;

    const-string v6, "movies"

    check-cast v6, Ljava/lang/CharSequence;

    const/4 v7, 0x0

    const/4 v8, 0x2

    const/4 v9, 0x0

    invoke-static {v5, v6, v7, v8, v9}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v5

    if-nez v5, :cond_464

    move-object v5, v0

    check-cast v5, Ljava/lang/CharSequence;

    const-string v6, "anime"

    check-cast v6, Ljava/lang/CharSequence;

    invoke-static {v5, v6, v7, v8, v9}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v5

    if-nez v5, :cond_464

    move-object v5, v0

    check-cast v5, Ljava/lang/CharSequence;

    const-string v6, "asian"

    check-cast v6, Ljava/lang/CharSequence;

    invoke-static {v5, v6, v7, v8, v9}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v5

    if-nez v5, :cond_464

    move-object v5, v0

    check-cast v5, Ljava/lang/CharSequence;

    const-string v6, "western"

    check-cast v6, Ljava/lang/CharSequence;

    invoke-static {v5, v6, v7, v8, v9}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v5

    if-nez v5, :cond_464

    move-object v5, v0

    check-cast v5, Ljava/lang/CharSequence;

    const-string v6, "category/"

    check-cast v6, Ljava/lang/CharSequence;

    invoke-static {v5, v6, v7, v8, v9}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_403

    goto :goto_464

    .line 257
    :cond_403
    move-object v5, v0

    check-cast v5, Ljava/lang/CharSequence;

    const-string v6, "trending"

    check-cast v6, Ljava/lang/CharSequence;

    invoke-static {v5, v6, v7, v8, v9}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_415

    .line 258
    invoke-static {v3}, Lcom/example/data/network/MovieBoxApiKt;->rewriteTrendingJson(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    goto :goto_468

    .line 259
    :cond_415
    move-object v5, v0

    check-cast v5, Ljava/lang/CharSequence;

    const-string v6, "stream"

    check-cast v6, Ljava/lang/CharSequence;

    const/4 v7, 0x0

    const/4 v8, 0x2

    const/4 v9, 0x0

    invoke-static {v5, v6, v7, v8, v9}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_42a

    .line 260
    invoke-static {v3}, Lcom/example/data/network/MovieBoxApiKt;->rewriteStreamJson(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    goto :goto_468

    :cond_42a
    move-object/from16 v5, v26

    .line 261
    invoke-static {v0, v5, v7, v8, v9}, Lkotlin/text/StringsKt;->endsWith$default(Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Z

    move-result v5

    if-nez v5, :cond_45f

    move-object v5, v0

    check-cast v5, Ljava/lang/CharSequence;

    check-cast v11, Ljava/lang/CharSequence;

    invoke-static {v5, v11, v7, v8, v9}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_43e

    goto :goto_45f

    .line 263
    :cond_43e
    move-object v5, v0

    check-cast v5, Ljava/lang/CharSequence;

    move-object v6, v12

    check-cast v6, Ljava/lang/CharSequence;

    invoke-static {v5, v6, v7, v8, v9}, Lkotlin/text/StringsKt;->contains$default(Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_469

    .line 264
    invoke-static {v0, v12, v9, v8, v9}, Lkotlin/text/StringsKt;->substringAfter$default(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    move-object/from16 v10, v24

    invoke-static {v0, v10, v9, v8, v9}, Lkotlin/text/StringsKt;->substringBefore$default(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    invoke-static {v0, v4, v9, v8, v9}, Lkotlin/text/StringsKt;->substringBefore$default(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    move-object/from16 v4, v23

    .line 265
    invoke-static {v3, v0, v4}, Lcom/example/data/network/MovieBoxApiKt;->rewriteDetailJson(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    goto :goto_468

    .line 262
    :cond_45f
    :goto_45f
    invoke-static {v3}, Lcom/example/data/network/MovieBoxApiKt;->rewriteSearchJson(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    goto :goto_468

    .line 256
    :cond_464
    :goto_464
    invoke-static {v3}, Lcom/example/data/network/MovieBoxApiKt;->flattenCategoryJson(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0
    :try_end_468
    .catch Ljava/lang/Exception; {:try_start_3ba .. :try_end_468} :catch_469

    :goto_468
    move-object v3, v0

    .line 273
    :catch_469
    :cond_469
    invoke-virtual {v2}, Lokhttp3/ResponseBody;->contentType()Lokhttp3/MediaType;

    move-result-object v0

    .line 274
    sget-object v2, Lokhttp3/ResponseBody;->Companion:Lokhttp3/ResponseBody$Companion;

    invoke-virtual {v2, v0, v3}, Lokhttp3/ResponseBody$Companion;->create(Lokhttp3/MediaType;Ljava/lang/String;)Lokhttp3/ResponseBody;

    move-result-object v0

    .line 275
    invoke-virtual {v1}, Lokhttp3/Response;->newBuilder()Lokhttp3/Response$Builder;

    move-result-object v1

    invoke-virtual {v1, v0}, Lokhttp3/Response$Builder;->body(Lokhttp3/ResponseBody;)Lokhttp3/Response$Builder;

    move-result-object v0

    invoke-virtual {v0}, Lokhttp3/Response$Builder;->build()Lokhttp3/Response;

    move-result-object v0

    return-object v0

    :cond_480
    return-object v1

    :cond_481
    move-object/from16 v18, v2

    move-object v3, v6

    move-object v10, v8

    move-object v6, v9

    move-object/from16 v19, v11

    const/4 v2, 0x1

    move-object v8, v1

    .line 281
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v11

    invoke-static {v11, v12}, Ljava/lang/String;->valueOf(J)Ljava/lang/String;

    move-result-object v1

    .line 282
    move-object v4, v1

    check-cast v4, Ljava/lang/CharSequence;

    invoke-static {v4}, Lkotlin/text/StringsKt;->reversed(Ljava/lang/CharSequence;)Ljava/lang/CharSequence;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v4

    .line 283
    invoke-static {v4}, Lcom/example/data/network/MovieBoxApiKt;->md5(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v4

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v9, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    const-string v11, ","

    invoke-virtual {v9, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    .line 286
    invoke-virtual/range {v18 .. v18}, Lokhttp3/Request;->url()Lokhttp3/HttpUrl;

    move-result-object v9

    .line 287
    invoke-virtual {v9}, Lokhttp3/HttpUrl;->queryParameterNames()Ljava/util/Set;

    move-result-object v11

    check-cast v11, Ljava/lang/Iterable;

    invoke-static {v11}, Lkotlin/collections/CollectionsKt;->sorted(Ljava/lang/Iterable;)Ljava/util/List;

    move-result-object v11

    move-object/from16 v21, v11

    check-cast v21, Ljava/lang/Iterable;

    const-string v11, "&"

    move-object/from16 v22, v11

    check-cast v22, Ljava/lang/CharSequence;

    new-instance v11, Lcom/example/data/network/MovieBoxSignatureInterceptor$$ExternalSyntheticLambda0;

    invoke-direct {v11, v9}, Lcom/example/data/network/MovieBoxSignatureInterceptor$$ExternalSyntheticLambda0;-><init>(Lokhttp3/HttpUrl;)V

    const/16 v28, 0x1e

    const/16 v29, 0x0

    const/16 v23, 0x0

    const/16 v24, 0x0

    const/16 v25, 0x0

    const/16 v26, 0x0

    move-object/from16 v27, v11

    invoke-static/range {v21 .. v29}, Lkotlin/collections/CollectionsKt;->joinToString$default(Ljava/lang/Iterable;Ljava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/CharSequence;ILjava/lang/CharSequence;Lkotlin/jvm/functions/Function1;ILjava/lang/Object;)Ljava/lang/String;

    move-result-object v11

    .line 297
    invoke-virtual/range {v18 .. v18}, Lokhttp3/Request;->body()Lokhttp3/RequestBody;

    move-result-object v12

    if-eqz v12, :cond_541

    .line 300
    :try_start_4ed
    new-instance v13, Lokio/Buffer;

    invoke-direct {v13}, Lokio/Buffer;-><init>()V

    .line 301
    move-object v14, v13

    check-cast v14, Lokio/BufferedSink;

    invoke-virtual {v12, v14}, Lokhttp3/RequestBody;->writeTo(Lokio/BufferedSink;)V

    .line 302
    invoke-virtual {v13}, Lokio/Buffer;->readByteArray()[B

    move-result-object v12

    .line 303
    array-length v13, v12

    if-nez v13, :cond_500

    goto :goto_501

    :cond_500
    const/4 v2, 0x0

    :goto_501
    if-nez v2, :cond_541

    .line 304
    array-length v2, v12

    invoke-static {v2}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v2
    :try_end_508
    .catch Ljava/lang/Exception; {:try_start_4ed .. :try_end_508} :catch_53c

    .line 305
    :try_start_508
    array-length v13, v12

    const v14, 0x19000

    invoke-static {v13, v14}, Ljava/lang/Math;->min(II)I

    move-result v13

    const/4 v14, 0x0

    .line 306
    invoke-static {v12, v14, v13}, Lkotlin/collections/ArraysKt;->copyOfRange([BII)[B

    move-result-object v12

    .line 307
    const-string v13, "MD5"

    invoke-static {v13}, Ljava/security/MessageDigest;->getInstance(Ljava/lang/String;)Ljava/security/MessageDigest;

    move-result-object v13

    .line 308
    invoke-virtual {v13, v12}, Ljava/security/MessageDigest;->digest([B)[B

    move-result-object v21

    .line 309
    invoke-static/range {v21 .. v21}, Lkotlin/jvm/internal/Intrinsics;->checkNotNull(Ljava/lang/Object;)V

    move-object/from16 v22, v19

    check-cast v22, Ljava/lang/CharSequence;

    new-instance v27, Lcom/example/data/network/MovieBoxSignatureInterceptor$$ExternalSyntheticLambda1;

    invoke-direct/range {v27 .. v27}, Lcom/example/data/network/MovieBoxSignatureInterceptor$$ExternalSyntheticLambda1;-><init>()V

    const/16 v28, 0x1e

    const/16 v29, 0x0

    const/16 v23, 0x0

    const/16 v24, 0x0

    const/16 v25, 0x0

    const/16 v26, 0x0

    invoke-static/range {v21 .. v29}, Lkotlin/collections/ArraysKt;->joinToString$default([BLjava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/CharSequence;ILjava/lang/CharSequence;Lkotlin/jvm/functions/Function1;ILjava/lang/Object;)Ljava/lang/String;

    move-result-object v12
    :try_end_53b
    .catch Ljava/lang/Exception; {:try_start_508 .. :try_end_53b} :catch_53e

    goto :goto_544

    :catch_53c
    move-object/from16 v2, v19

    :catch_53e
    move-object/from16 v12, v19

    goto :goto_544

    :cond_541
    move-object/from16 v2, v19

    move-object v12, v2

    .line 316
    :goto_544
    invoke-virtual/range {v18 .. v18}, Lokhttp3/Request;->method()Ljava/lang/String;

    move-result-object v13

    .line 317
    invoke-virtual {v9}, Lokhttp3/HttpUrl;->encodedPath()Ljava/lang/String;

    move-result-object v9

    move-object v14, v11

    check-cast v14, Ljava/lang/CharSequence;

    invoke-interface {v14}, Ljava/lang/CharSequence;->length()I

    move-result v14

    if-lez v14, :cond_563

    new-instance v14, Ljava/lang/StringBuilder;

    invoke-direct {v14, v10}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v14, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    goto :goto_565

    :cond_563
    move-object/from16 v10, v19

    :goto_565
    new-instance v11, Ljava/lang/StringBuilder;

    invoke-direct {v11}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v11, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    .line 320
    new-instance v10, Ljava/lang/StringBuilder;

    invoke-direct {v10}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v10, v13}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    const-string v11, "\napplication/json\napplication/json;charset=UTF-8\n"

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v10, "\n"

    invoke-virtual {v2, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    .line 325
    :try_start_5a7
    const-string v9, "76iRl07s0xSN9jqmEWAt79EBJZulIQIsV64FZr2O"
    :try_end_5a9
    .catch Ljava/lang/Exception; {:try_start_5a7 .. :try_end_5a9} :catch_5e9

    const/4 v14, 0x0

    .line 327
    :try_start_5aa
    invoke-static {v9, v14}, Landroid/util/Base64;->decode(Ljava/lang/String;I)[B

    move-result-object v9
    :try_end_5ae
    .catch Ljava/lang/Exception; {:try_start_5aa .. :try_end_5ae} :catch_5af

    goto :goto_5ba

    .line 329
    :catch_5af
    :try_start_5af
    sget-object v10, Lkotlin/text/Charsets;->UTF_8:Ljava/nio/charset/Charset;

    invoke-virtual {v9, v10}, Ljava/lang/String;->getBytes(Ljava/nio/charset/Charset;)[B

    move-result-object v9

    const-string v10, "getBytes(...)"

    invoke-static {v9, v10}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V

    .line 331
    :goto_5ba
    invoke-static {v9}, Lkotlin/jvm/internal/Intrinsics;->checkNotNull(Ljava/lang/Object;)V

    sget-object v10, Lkotlin/text/Charsets;->UTF_8:Ljava/nio/charset/Charset;

    invoke-virtual {v2, v10}, Ljava/lang/String;->getBytes(Ljava/nio/charset/Charset;)[B

    move-result-object v2

    const-string v10, "getBytes(...)"

    invoke-static {v2, v10}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V

    invoke-static {v9, v2}, Lcom/example/data/network/MovieBoxApiKt;->hmacMd5([B[B)[B

    move-result-object v2

    const/4 v9, 0x2

    .line 332
    invoke-static {v2, v9}, Landroid/util/Base64;->encodeToString([BI)Ljava/lang/String;

    move-result-object v2

    .line 333
    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v9, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v9, "|2|"

    invoke-virtual {v1, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v11
    :try_end_5e8
    .catch Ljava/lang/Exception; {:try_start_5af .. :try_end_5e8} :catch_5e9

    goto :goto_5eb

    :catch_5e9
    move-object/from16 v11, v19

    .line 339
    :goto_5eb
    invoke-virtual/range {v18 .. v18}, Lokhttp3/Request;->newBuilder()Lokhttp3/Request$Builder;

    move-result-object v1

    .line 340
    invoke-virtual {v1, v7, v3}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v1

    .line 341
    const-string v2, "X-M-Version"

    const-string v3, "16.2.1"

    invoke-virtual {v1, v2, v3}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v1

    .line 342
    invoke-virtual {v1, v5, v6}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v1

    .line 343
    const-string v2, "Content-Type"

    const-string v3, "application/json;charset=UTF-8"

    invoke-virtual {v1, v2, v3}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v1

    .line 344
    const-string v2, "Referer"

    const-string v3, "https://api6.aoneroom.com/"

    invoke-virtual {v1, v2, v3}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v1

    .line 345
    const-string v2, "X-Client-Token"

    invoke-virtual {v1, v2, v4}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v1

    .line 346
    const-string v2, "x-tr-signature"

    invoke-virtual {v1, v2, v11}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v1

    .line 347
    const-string v2, "X-Play-Mode"

    const-string v3, "2"

    invoke-virtual {v1, v2, v3}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v1

    .line 350
    iget-object v2, v0, Lcom/example/data/network/MovieBoxSignatureInterceptor;->prefs:Lcom/example/data/preferences/PreferencesManager;

    invoke-virtual {v2}, Lcom/example/data/preferences/PreferencesManager;->getSpCode()Ljava/lang/String;

    move-result-object v2

    check-cast v2, Ljava/lang/CharSequence;

    invoke-interface {v2}, Ljava/lang/CharSequence;->length()I

    move-result v2

    if-lez v2, :cond_638

    iget-object v2, v0, Lcom/example/data/network/MovieBoxSignatureInterceptor;->prefs:Lcom/example/data/preferences/PreferencesManager;

    invoke-virtual {v2}, Lcom/example/data/preferences/PreferencesManager;->getSpCode()Ljava/lang/String;

    move-result-object v2

    goto :goto_63a

    :cond_638
    const-string v2, "40401"

    .line 351
    :goto_63a
    iget-object v3, v0, Lcom/example/data/network/MovieBoxSignatureInterceptor;->prefs:Lcom/example/data/preferences/PreferencesManager;

    invoke-virtual {v3}, Lcom/example/data/preferences/PreferencesManager;->getLocalIso()Ljava/lang/String;

    move-result-object v3

    check-cast v3, Ljava/lang/CharSequence;

    invoke-interface {v3}, Ljava/lang/CharSequence;->length()I

    move-result v3

    if-lez v3, :cond_65a

    iget-object v0, v0, Lcom/example/data/network/MovieBoxSignatureInterceptor;->prefs:Lcom/example/data/preferences/PreferencesManager;

    invoke-virtual {v0}, Lcom/example/data/preferences/PreferencesManager;->getLocalIso()Ljava/lang/String;

    move-result-object v0

    sget-object v3, Ljava/util/Locale;->ROOT:Ljava/util/Locale;

    invoke-virtual {v0, v3}, Ljava/lang/String;->toUpperCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object v0

    const-string v3, "toUpperCase(...)"

    invoke-static {v0, v3}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V

    goto :goto_65c

    :cond_65a
    const-string v0, "IN"

    .line 352
    :goto_65c
    const-string v3, "US"

    invoke-static {v0, v3}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_667

    const-string v3, "America/New_York"

    goto :goto_669

    :cond_667
    const-string v3, "Asia/Kolkata"

    .line 370
    :goto_669
    new-instance v4, Ljava/lang/StringBuilder;

    const-string v5, "\n            {\n              \"package_name\": \"com.community.oneroom\",\n              \"version_name\": \"3.0.03.0529.03\",\n              \"version_code\": 50020042,\n              \"os\": \"android\",\n              \"os_version\": \"12\",\n              \"install_ch\": \"ps\",\n              \"device_id\": \"8c5da15be6ca34e724a27bc102cd8bcf\",\n              \"install_store\": \"ps\",\n              \"gaid\": \"4b6c3748-8dfa-4fb6-bda9-163a98e8dfa9\",\n              \"brand\": \"Google\",\n              \"model\": \"Pixel 6\",\n              \"system_language\": \"en\",\n              \"net\": \"NETWORK_WIFI\",\n              \"region\": \""

    invoke-direct {v4, v5}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v4, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v4, "\",\n              \"timezone\": \""

    invoke-virtual {v0, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v3, "\",\n              \"sp_code\": \""

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v2, "\",\n              \"X-Play-Mode\": \"2\"\n            }\n        "

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 373
    invoke-static {v0}, Lkotlin/text/StringsKt;->trimIndent(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    const/4 v6, 0x4

    const/4 v7, 0x0

    const-string v3, "\n"

    const-string v4, ""

    const/4 v5, 0x0

    invoke-static/range {v2 .. v7}, Lkotlin/text/StringsKt;->replace$default(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Ljava/lang/String;

    move-result-object v9

    const/4 v13, 0x4

    const/4 v14, 0x0

    const-string v10, " "

    const-string v11, ""

    const/4 v12, 0x0

    invoke-static/range {v9 .. v14}, Lkotlin/text/StringsKt;->replace$default(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    .line 375
    const-string v2, "X-Client-Info"

    invoke-virtual {v1, v2, v0}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    .line 377
    invoke-virtual {v1}, Lokhttp3/Request$Builder;->build()Lokhttp3/Request;

    move-result-object v0

    invoke-interface {v8, v0}, Lokhttp3/Interceptor$Chain;->proceed(Lokhttp3/Request;)Lokhttp3/Response;

    move-result-object v0

    return-object v0
.end method
