.class public final synthetic Lcom/example/data/network/MovieBoxSignatureInterceptor$$ExternalSyntheticLambda0;
.super Ljava/lang/Object;
.source "D8$$SyntheticClass"

# interfaces
.implements Lkotlin/jvm/functions/Function1;


# instance fields
.field public final synthetic f$0:Lokhttp3/HttpUrl;


# direct methods
.method public synthetic constructor <init>(Lokhttp3/HttpUrl;)V
    .registers 2

    .line 0
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/example/data/network/MovieBoxSignatureInterceptor$$ExternalSyntheticLambda0;->f$0:Lokhttp3/HttpUrl;

    return-void
.end method


# virtual methods
.method public final invoke(Ljava/lang/Object;)Ljava/lang/Object;
    .registers 2

    .line 0
    iget-object p0, p0, Lcom/example/data/network/MovieBoxSignatureInterceptor$$ExternalSyntheticLambda0;->f$0:Lokhttp3/HttpUrl;

    check-cast p1, Ljava/lang/String;

    invoke-static {p0, p1}, Lcom/example/data/network/MovieBoxSignatureInterceptor;->intercept$lambda$2(Lokhttp3/HttpUrl;Ljava/lang/String;)Ljava/lang/CharSequence;

    move-result-object p0

    return-object p0
.end method
