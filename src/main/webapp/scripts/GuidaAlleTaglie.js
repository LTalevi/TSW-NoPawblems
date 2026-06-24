function cambiaTab(animale) {
    document.querySelectorAll('.btn-tab').forEach(btn => {
        btn.classList.remove('attivo');
    });

    document.querySelectorAll('.sezione-taglie').forEach(sezione => {
        sezione.classList.remove('attiva');
    });

    const bottoneCliccato = document.querySelector(`.btn-tab[onclick="cambiaTab('${animale}')"]`);
    if (bottoneCliccato) bottoneCliccato.classList.add('attivo');

    const tabellaDaMostrare = document.getElementById(`tab-${animale}`);
    if (tabellaDaMostrare) tabellaDaMostrare.classList.add('attiva');
}