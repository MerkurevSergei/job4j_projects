<div class="col">
    <div class="card card bg-light" style="width: 18rem;">
        <div class="card-body pb-2">
            <h5 class="card-title">{{:title}}</h5>
            <p class="card-text">{{:description}}</p>
            <div>
                {{for categories}}
                <span class="badge {{:~getCategoryClass(#index)}}">{{:name}}</span>
                {{/for}}
            </div>
        </div>
        <div class="card-footer">
            <div class="card-footer__left form-check form-check-inline form-switch pb-0 mb-0 align-bottom">
                <label>
                    <input class="card-check-complete form-check-input" type="checkbox" role="button" value="{{:id}}"
                           {{:checked}}>
                </label>
            </div>
            <small class="card-footer__right text-muted align-top text-right">{{:created}}</small>
        </div>
    </div>
</div>