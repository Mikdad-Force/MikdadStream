.class public final synthetic Lcom/example/data/network/LocalMovieBoxServer$$ExternalSyntheticLambda4;
.super Ljava/lang/Object;
.source "D8$$SyntheticClass"

# interfaces
.implements Ljava/util/Comparator;


# instance fields
.field public final synthetic f$0:Lkotlin/jvm/functions/Function2;


# direct methods
.method public synthetic constructor <init>(Lkotlin/jvm/functions/Function2;)V
    .registers 2

    .line 0
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/example/data/network/LocalMovieBoxServer$$ExternalSyntheticLambda4;->f$0:Lkotlin/jvm/functions/Function2;

    return-void
.end method


# virtual methods
.method public final compare(Ljava/lang/Object;Ljava/lang/Object;)I
    .registers 3

    .line 0
    iget-object p0, p0, Lcom/example/data/network/LocalMovieBoxServer$$ExternalSyntheticLambda4;->f$0:Lkotlin/jvm/functions/Function2;

    invoke-static {p0, p1, p2}, Lcom/example/data/network/LocalMovieBoxServer;->routeRequest$lambda$16(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Ljava/lang/Object;)I

    move-result p0

    return p0
.end method
