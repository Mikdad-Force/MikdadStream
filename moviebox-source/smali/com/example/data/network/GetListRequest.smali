.class public final Lcom/example/data/network/GetListRequest;
.super Ljava/lang/Object;
.source "MovieBoxApi.kt"


# annotations
.annotation runtime Lcom/squareup/moshi/JsonClass;
    generateAdapter = true
.end annotation

.annotation runtime Lkotlin/Metadata;
    d1 = {
        "\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0008\n\u0002\u0008\r\n\u0002\u0010\u000b\n\u0002\u0008\u0003\n\u0002\u0010\u000e\n\u0000\u0008\u0087\u0008\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\u0004\u0008\u0006\u0010\u0007J\t\u0010\u000c\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\r\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000e\u001a\u00020\u0003H\u00c6\u0003J\'\u0010\u000f\u001a\u00020\u00002\u0008\u0008\u0002\u0010\u0002\u001a\u00020\u00032\u0008\u0008\u0002\u0010\u0004\u001a\u00020\u00032\u0008\u0008\u0002\u0010\u0005\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u0010\u001a\u00020\u00112\u0008\u0010\u0012\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0013\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u0014\u001a\u00020\u0015H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0008\n\u0000\u001a\u0004\u0008\u0008\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0008\n\u0000\u001a\u0004\u0008\n\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\u0008\n\u0000\u001a\u0004\u0008\u000b\u0010\t\u00a8\u0006\u0016"
    }
    d2 = {
        "Lcom/example/data/network/GetListRequest;",
        "",
        "categoryId",
        "",
        "page",
        "pageSize",
        "<init>",
        "(III)V",
        "getCategoryId",
        "()I",
        "getPage",
        "getPageSize",
        "component1",
        "component2",
        "component3",
        "copy",
        "equals",
        "",
        "other",
        "hashCode",
        "toString",
        "",
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


# instance fields
.field private final categoryId:I

.field private final page:I

.field private final pageSize:I


# direct methods
.method static constructor <clinit>()V
    .registers 0

    return-void
.end method

.method public constructor <init>(III)V
    .registers 4

    .line 20
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 21
    iput p1, p0, Lcom/example/data/network/GetListRequest;->categoryId:I

    .line 22
    iput p2, p0, Lcom/example/data/network/GetListRequest;->page:I

    .line 23
    iput p3, p0, Lcom/example/data/network/GetListRequest;->pageSize:I

    return-void
.end method

.method public static synthetic copy$default(Lcom/example/data/network/GetListRequest;IIIILjava/lang/Object;)Lcom/example/data/network/GetListRequest;
    .registers 6

    and-int/lit8 p5, p4, 0x1

    if-eqz p5, :cond_6

    iget p1, p0, Lcom/example/data/network/GetListRequest;->categoryId:I

    :cond_6
    and-int/lit8 p5, p4, 0x2

    if-eqz p5, :cond_c

    iget p2, p0, Lcom/example/data/network/GetListRequest;->page:I

    :cond_c
    and-int/lit8 p4, p4, 0x4

    if-eqz p4, :cond_12

    iget p3, p0, Lcom/example/data/network/GetListRequest;->pageSize:I

    :cond_12
    invoke-virtual {p0, p1, p2, p3}, Lcom/example/data/network/GetListRequest;->copy(III)Lcom/example/data/network/GetListRequest;

    move-result-object p0

    return-object p0
.end method


# virtual methods
.method public final component1()I
    .registers 1

    iget p0, p0, Lcom/example/data/network/GetListRequest;->categoryId:I

    return p0
.end method

.method public final component2()I
    .registers 1

    iget p0, p0, Lcom/example/data/network/GetListRequest;->page:I

    return p0
.end method

.method public final component3()I
    .registers 1

    iget p0, p0, Lcom/example/data/network/GetListRequest;->pageSize:I

    return p0
.end method

.method public final copy(III)Lcom/example/data/network/GetListRequest;
    .registers 4

    new-instance p0, Lcom/example/data/network/GetListRequest;

    invoke-direct {p0, p1, p2, p3}, Lcom/example/data/network/GetListRequest;-><init>(III)V

    return-object p0
.end method

.method public equals(Ljava/lang/Object;)Z
    .registers 6

    const/4 v0, 0x1

    if-ne p0, p1, :cond_4

    return v0

    :cond_4
    instance-of v1, p1, Lcom/example/data/network/GetListRequest;

    const/4 v2, 0x0

    if-nez v1, :cond_a

    return v2

    :cond_a
    check-cast p1, Lcom/example/data/network/GetListRequest;

    iget v1, p0, Lcom/example/data/network/GetListRequest;->categoryId:I

    iget v3, p1, Lcom/example/data/network/GetListRequest;->categoryId:I

    if-eq v1, v3, :cond_13

    return v2

    :cond_13
    iget v1, p0, Lcom/example/data/network/GetListRequest;->page:I

    iget v3, p1, Lcom/example/data/network/GetListRequest;->page:I

    if-eq v1, v3, :cond_1a

    return v2

    :cond_1a
    iget p0, p0, Lcom/example/data/network/GetListRequest;->pageSize:I

    iget p1, p1, Lcom/example/data/network/GetListRequest;->pageSize:I

    if-eq p0, p1, :cond_21

    return v2

    :cond_21
    return v0
.end method

.method public final getCategoryId()I
    .registers 1

    .line 21
    iget p0, p0, Lcom/example/data/network/GetListRequest;->categoryId:I

    return p0
.end method

.method public final getPage()I
    .registers 1

    .line 22
    iget p0, p0, Lcom/example/data/network/GetListRequest;->page:I

    return p0
.end method

.method public final getPageSize()I
    .registers 1

    .line 23
    iget p0, p0, Lcom/example/data/network/GetListRequest;->pageSize:I

    return p0
.end method

.method public hashCode()I
    .registers 3

    iget v0, p0, Lcom/example/data/network/GetListRequest;->categoryId:I

    invoke-static {v0}, Ljava/lang/Integer;->hashCode(I)I

    move-result v0

    mul-int/lit8 v0, v0, 0x1f

    iget v1, p0, Lcom/example/data/network/GetListRequest;->page:I

    invoke-static {v1}, Ljava/lang/Integer;->hashCode(I)I

    move-result v1

    add-int/2addr v0, v1

    mul-int/lit8 v0, v0, 0x1f

    iget p0, p0, Lcom/example/data/network/GetListRequest;->pageSize:I

    invoke-static {p0}, Ljava/lang/Integer;->hashCode(I)I

    move-result p0

    add-int/2addr v0, p0

    return v0
.end method

.method public toString()Ljava/lang/String;
    .registers 5

    iget v0, p0, Lcom/example/data/network/GetListRequest;->categoryId:I

    iget v1, p0, Lcom/example/data/network/GetListRequest;->page:I

    iget p0, p0, Lcom/example/data/network/GetListRequest;->pageSize:I

    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "GetListRequest(categoryId="

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v2, ", page="

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, ", pageSize="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object p0

    const-string v0, ")"

    invoke-virtual {p0, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method
