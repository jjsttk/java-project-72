@import hexlet.code.dto.urls.UrlsPage
@import hexlet.code.util.view.DateTimeUtils
@import hexlet.code.util.view.NamedRoutes
@param UrlsPage page
@template.layout.page(
page = page,
content = @`
    <div class="container-lg mt-5">
        <h1>Сайты</h1>
        <table class="table table-bordered table-hover mt-3">
            <thead>
            <tr>
                <th class="col-1">ID</th>
                <th>Имя</th>
                <th class="col-2">Последняя проверка</th>
                <th class="col-1">Код ответа</th>
            </tr>
            </thead>
            @if(!page.getUrlsMap().isEmpty())
            <tbody>
                @for(var entry : page.getUrlsMap().entrySet())
                    <tr>
                        <td>${entry.getKey().getId()}</td>
                        <td><a href="${NamedRoutes.urlPath(String.valueOf(entry.getKey().getId()))}">${entry.getKey().getName()}</a></td>
                        @if(entry.getValue() != null)
                            <td>${DateTimeUtils.formatTimestamp(entry.getValue().getCreatedAt())}</td>
                            <td>${entry.getValue().getStatusCode()}</td>
                        @else
                            <td></td>
                            <td></td>
                        @endif
                    </tr>
                @endfor
            </tbody>
            @endif
        </table>
    </div>
`)